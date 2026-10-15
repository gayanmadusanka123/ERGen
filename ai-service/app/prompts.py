import os
from pydantic import BaseModel, Field
import instructor
from google import genai

class EventDetails(BaseModel):
    event_name: str
    date: str
    location: str
    attendees_count: int = Field(description="Approximate number of people")

os.environ["GEMINI_API_KEY"] = "AQ.Ab8RN6LPwDxLgSeLVcG6mTAk3yFsnStIn88fkkD-IhJk2NoFrQ"

genai_client = genai.Client()

client = instructor.from_provider("google/gemini-3.8-flash")

user_input_text = "Hey, we are hosting TechCon 2026 on November 14th at the Boston Convention Center. We are expecting around 500 people to show up."

extracted_event: EventDetails = client.chat.completions.create(
    response_model=EventDetails,
    messages=[
        {
            "role": "user",
            "content": user_input_text
        }
    ]
)

print(extracted_event.model_dump_json(indent=2))