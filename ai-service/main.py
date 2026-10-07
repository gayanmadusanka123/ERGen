from fastapi import FastAPI
from pydantic import BaseModel
from typing import Optional

from app.planner import AIPlanner
from app.models import UISpecification

app = FastAPI()
planner = AIPlanner()

class AIRequest(BaseModel):
    user_description: str
    entities: list
    previous_specification: Optional[dict] = None

@app.post("/plan")
def create_plan(request: AIRequest):

    specification = planner.generate(
        request.user_description,
        request.entities,
        request.previous_specification
    )

    return specification