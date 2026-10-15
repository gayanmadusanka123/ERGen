import os
from pydantic import BaseModel, Field
from typing import Optional, List
import instructor
from google import genai

class UIComponent(BaseModel):
    type: str = Field(description="The type of UI element or layout control, e.g., 'metric', 'table', 'form', 'card', 'chart'")
    entity: Optional[str] = Field(
        default=None,
        description="The domain or data entity mapped to this component, e.g., 'Employee', 'Department'"
    )
    features: list[str] = Field(
        default_factory=list,
        description="Interactive capabilities supported by this component, e.g., ['create', 'edit', 'delete', 'search', 'count']"
    )

class UIPage(BaseModel):
    name: str = Field(description="Display name or header title of the page, e.g., 'Dashboard', 'Employees'")
    entity: Optional[str] = Field(
        default=None,
        description="The primary data entity managed by this page, e.g., 'Employee'"
    )
    route: Optional[str] = Field(
        default=None,
        description="The URL route path for navigation, e.g., '/dashboard', '/employees'"
    )
    view: Optional[str] = Field(
        default=None,
        description="The structural layout pattern for the page, e.g., 'dashboard', 'crud', 'grid', 'detail'"
    )

    componentClass: Optional[str] = Field(
        default=None,
        description="The generated frontend framework component or class name, e.g., 'EmployeeDashboardPage'"
    )
    componentFile: Optional[str] = Field(
        default=None,
        description="The target file system path for code generation e.g. .ts files"
    )

    components: list[UIComponent] = Field(
        default_factory=list,
        description="List of UI components rendered inside this page"
    )

class UISpecification(BaseModel):
    name: str = Field(description="The main title or name of the software system, e.g., 'Employee Management'")
    layout: str = Field(description="Global application shell navigation pattern, e.g., 'sidebar', 'top-header', 'stacked'")
    pages: list[UIPage] = Field(
        default_factory=list,
        description="The list of pages that make up the full application UI"
    )

os.environ["GEMINI_API_KEY"] = "AQ.Ab8RN6LPwDxLgSeLVcG6mTAk3yFsnStIn88fkkD-IhJk2NoFrQ"

genai_client = genai.Client()

client = instructor.from_provider("google/gemini-3.5-flash-lite")

user_input_description = "Create an application called 'Employee Hub' using a sidebar navigation. Include two pages: a 'Dashboard' page using a dashboard view that contains a metric card for employee count, and an 'Employees' page using a crud view that contains a table component for managing employees with create, edit, delete, and search actions."

extracted_specification: UISpecification = client.chat.completions.create(
    response_model=UISpecification,
    messages=[
        {
            "role": "user",
            "content": user_input_description
        }
    ]
)

print(extracted_specification.model_dump_json(indent=2))
