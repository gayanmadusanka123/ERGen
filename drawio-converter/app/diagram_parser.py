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
        r"<br\s*/?>|</div>|</p>",
        " ",
        value,
        flags=re.IGNORECASE,
    )
    value = re.sub(r"<[^>]+>", "", value)

    replacements = {
        "&amp;": "&",
        "&lt;": "<",
        "&gt;": ">",
        "&quot;": '"',
        "&#39;": "'",
        "&nbsp;": " ",
    }

    for encoded, decoded in replacements.items():
        value = value.replace(encoded, decoded)

    return " ".join(value.split()).strip()

def parse_drawio_xml(drawio_xml: str) -> ParsedDiagram:

    root = ET.fromstring(drawio_xml)

    cells = root.findall(".//mxCell")

    entities = []
    relationships = []
    warnings =[]

    entity_ids = set()
    relationship_ids = set()

    for cell in cells:
        if cell.get("vertex") != "1":
            continue

        cell_id = cell.get("id", "")
        label = clean_label(cell.get("value", ""))
        style = cell.get("style", "").lower()

        if not label:
            continue

        #rectangle shapes not rhombuses
        if(
            "rhombus" not in style
            and "ellipse" not in style
            and "shape=doubleellipse" not in style
            and (
                "rounded=0" in style
                or "shape=rectangle" in style
                or style.startswith("whiteSpace=")
            )
        ):
            entities.append(DiagramEntity(name=label))
            entity_ids.add(cell_id)

        #relationships(rhombuses)
        elif "rhombus" in style:
            relationships.append(DiagramRelationship(name=label))
            relationship_ids.add(cell_id)

    if not entities:
        warnings.append(
            "No entity rectangles were recognized. "
            "Check the Draw.io shape styles."
        )

    if not relationships:
        warnings.append(
            "No relationship diamonds were recognized."
        )

    return ParsedDiagram(
        entities=entities,
        relationships=relationships,
        warnings=warnings
    )