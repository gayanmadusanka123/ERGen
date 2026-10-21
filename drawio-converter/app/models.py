from pydantic import BaseModel, Field

class ConvertRequest(BaseModel):
    drawio_xml: str = Field(
        min_length=1,
        description="Complete Draw.io XML document"
    )

    database_name: str = "generated_db_new"
    username: str = "postgres"
    password: str = "1234"
    host: str = "localhost"
    port: str = "5433"

class DiagramAttribute(BaseModel):
    name: str
    data_type: str = "String"
    primary_key: bool = False
    required: bool = True
    derived: bool = False
    multivalued: bool = False

class DiagramEntity(BaseModel):
    name: str
    attributes: list[DiagramAttribute] = Field(
        default_factory=list
    )

class DiagramRelationship(BaseModel):
    name: str
    entities: list[str] = Field(default_factory=list)
    cardinalities: list[str] = Field(default_factory=list)

class ParsedDiagram(BaseModel):
    entities: list[DiagramEntity] = Field(default_factory=list)
    relationships: list[DiagramRelationship] = Field(default_factory=list)

class ConvertResponse(BaseModel):
    er_xml: str
    warnings: list[str] = Field(default_factory=list)