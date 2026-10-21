import math
import html
import re
from defusedxml import ElementTree as ET

from app.models import(
    DiagramAttribute,
    DiagramEntity,
    DiagramRelationship,
    ParsedDiagram,
)

def clean_label(value: str | None) -> str:
    #convert common draw.io HTML labels into plain text
    if not value:
        return ""

    value = re.sub(
        r"</?(div|p|br)\b[^>]*>",
        " ",
        value,
        flags=re.IGNORECASE,
    )
    value = re.sub(r"<[^>]+>", "", value)

    #decode entities such as &amp; and &lt
    value = html.unescape(value)

    return " ".join(value.split()).strip()

def get_shape_kind(cell) -> str | None:
    """Classify a Draw.io vertex as an entity, attribute,
    relationship, or text label."""

    style = cell.get("style", "").lower()
    value = cell.get("value", "")

    if cell.get("vertex") != "1":
        return None

    if style.startswith("text;"):
        return "text"

    if "rhombus" in style:
        return "relationship"

    if "ellipse" in style:
        return "attribute"

    if ("rounded=0" in style or "shape=rectangle" in style):
        return "entity"

    return None

def get_geometry(cell):
    #Return shape position and size from mxGeometry
    geometry = cell.find("mxGeometry")

    if geometry is None:
        return None

    try:
        return{
            "x": float(geometry.get("x", "0")),
            "y": float(geometry.get("y", "0")),
            "width": float(geometry.get("width", "0")),
            "height": float(geometry.get("height", "0")),
        }
    except ValueError:
        return None

def distance_to_shape(point, shape) -> float:
    #Measure distance from a point to a shape's rectangle

    x, y = point
    left = shape["x"]
    top = shape["y"]
    right = left + shape["width"]
    bottom = top + shape["height"]

    #Distance outside the rectangle in each direction
    dx = max(left - x, 0, x - right)
    dy = max(top - y, 0, y - bottom)

    return math.hypot(dx, dy)

def infer_shape_id(point, shapes, warnings):
    """Find the closest shape to a connector endpoint.

    Returns None when no shape is close enough or when the
    closest candidates are too ambiguous.
    """

    if point is None:
        return None

    candidates = sorted(
        (
            (distance_to_shape(point, shape["geometry"]), shape_id)
            for shape_id, shape in shapes.items()
            if shape["geometry"] is not None
        ),
        key=lambda item: item[0]
    )

    if not candidates or candidates[0][0] > 40:
        return None

    # If two shapes are almost equally close, do not guess.
    if (
        len(candidates) > 1
        and abs(candidates[1][0] - candidates[0][0]) < 3
    ):
        warnings.append(
            f"Ambiguous connector endpoint near {point}; "
            "the nearest shapes are too close to distinguish."
        )
        return None

    return candidates[0][1]

def get_edge_endpoints(edge, shapes, warnings):
    source_id = edge.get("source")
    target_id = edge.get("target")

    geometry = edge.find("mxGeometry")
    source_point = None
    target_point = None

    if geometry is not None:
        for point in geometry.findall("mxPoint"):
            coordinates = (
                point.get("x"),
                point.get("y"),
            )

            if None in coordinates:
                continue

            try:
                xy = (
                    float(coordinates[0]),
                    float(coordinates[1]),
                )
            except ValueError:
                continue

            if point.get("as") == "sourcePoint":
                source_point = xy
            elif point.get("as") == "targetPoint":
                target_point = xy

    # Explicit source/target IDs are more reliable than coordinates.
    if source_id not in shapes:
        source_id = infer_shape_id(source_point, shapes, warnings)

    if target_id not in shapes:
        target_id = infer_shape_id(target_point, shapes, warnings)

    return source_id, target_id

def parse_drawio_xml(drawio_xml: str) -> ParsedDiagram:

    root = ET.fromstring(drawio_xml)
    cells = root.findall(".//mxCell")

    warnings = []
    shapes = {}

    # First pass: identify shapes and remember their geometry.
    for cell in cells:
        kind = get_shape_kind(cell)

        if kind is None:
            continue

        shape_id = cell.get("id")
        if not shape_id:
            warnings.append("Found a shape without an ID.")
            continue

        raw_value = cell.get("value", "")
        geometry = get_geometry(cell)

        shapes[shape_id] = {
            "kind": kind,
            "raw_value": raw_value,
            "name": clean_label(raw_value),
            "style": cell.get("style", ""),
            "geometry": geometry,
        }

    entities_by_id = {}
    relationships_by_id = {}

    for shape_id, shape in shapes.items():
        if shape["kind"] == "entity":
            if not shape["name"]:
                continue

            entities_by_id[shape_id] = DiagramEntity(name=shape["name"])

        elif shape["kind"] == "relationship":
            if not shape["name"]:
                continue

            relationships_by_id[shape_id] = DiagramRelationship(name=shape["name"])

    for edge in cells:
        if edge.get("edge") != "1":
            continue

        source_id, target_id = get_edge_endpoints(edge, shapes, warnings)

        if source_id is None or target_id is None:
            warnings.append(
                f"Could not reliably resolve both endpoints "
                f"of connector '{edge.get('id', 'unknown')}'."
            )
            continue

        source = shapes[source_id]
        target = shapes[target_id]

        pair = {source["kind"], target["kind"]}

        #Entity Attribute connection.
        if pair == {"entity", "attribute"}:
            entity_id = (
                source_id
                if source["kind"] == "entity"
                else target_id
            )
            attribute_id = (
                source_id
                if source["kind"] == "attribute"
                else target_id
            )

            entity = entities_by_id.get(entity_id)
            attribute_shape = shapes[attribute_id]

            if entity is None or not attribute_shape["name"]:
                continue

            style = attribute_shape["style"].lower()
            raw_value = attribute_shape["raw_value"]

            is_primary_key = bool(
                re.search(r"<u(?:\s|>)", raw_value, re.I)
            )

            is_derived = "dashed=1" in style

            is_multivalued = "doubleellipse" in style

            entity.attributes.append(
                DiagramAttribute(
                    name=attribute_shape["name"],
                    data_type="Long" if is_primary_key
                    and attribute_shape["name"].lower() == "id"
                    else "String",
                    primary_key=is_primary_key,
                    required=True,
                    derived=is_derived,
                    multivalued=is_multivalued,
                )
            )
        #Entity Relationship connection
        elif pair == {"entity", "relationship"}:
            entity_id = (
                source_id
                if source["kind"] == "entity"
                else target_id
            )
            relationship_id = (
                source_id
                if source["kind"] == "relationship"
                else target_id
            )

            entity = entities_by_id.get(entity_id)
            relationship = relationships_by_id.get(relationship_id)

            if entity is not None and relationship is not None:
                if entity.name not in relationship.entities:
                    relationship.entities.append(entity.name)

    entities = list(entities_by_id.values())
    relationships = list(relationships_by_id.values())

    if not entities:
        warnings.append("No entity rectangles were recognized.")

    if not relationships:
        warnings.append("No relationship diamonds were recognized.")

    for relationship in relationships:
        if len(relationship.entities) < 2:
            warnings.append(
                f"Relationship '{relationship.name}' has fewer than two recognized entity connections."
            )

    return ParsedDiagram(
        entities=entities,
        relationships=relationships,
        warnings=warnings
    )
        

        

    