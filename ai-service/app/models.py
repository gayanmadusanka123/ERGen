from pydantic import BaseModel
from typing import Optional

class UIComponent(BaseModel):
    type: str
    entity: Optional[str] = None
    features: list[str] = []

class UIPage(BaseModel):
    name: str
    entity: Optional[str] = None
    route: Optional[str] = None
    view: Optional[str] = None

    componentClass: Optional[str] = None
    componentFile: Optional[str] = None

    components: list[UIComponent] = []

class UISpecification(BaseModel):
    name: str
    layout: str
    pages: list[UIPage] = []