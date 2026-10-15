from fastapi import FastAPI
from pydantic import BaseModel
from typing import Any

from app.models import UISpecification
from app.planner import generate_ui_specification

app = FastAPI()

class AIRequest(BaseModel):
    user_description: str
    er_model: dict[str, Any]
    previous_specification: dict[str, Any] | None = None

@app.post("/plan", response_model=UISpecification)
def create_plan(request: AIRequest):
    return generate_ui_specification(
        user_description=request.user_description,
        er_model=request.er_model,
        previous_specification=request.previous_specification,
    )
