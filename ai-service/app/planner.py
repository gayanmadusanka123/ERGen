import json
import instructor

from .models import UISpecification

# $env:GEMINI_API_KEY="AQ.Ab8RN6JVrIuo2YUu_4Y8UpfguoHeRKDJmctx2bXzdjiXXXq0FQ"
# api_key = "AQ.Ab8RN6JVrIuo2YUu_4Y8UpfguoHeRKDJmctx2bXzdjiXXXq0FQ"

# to run the fastapi
# python -m uvicorn main:app --reload


# Initialize the Gemini client
client = instructor.from_provider(
    "google/gemini-3.5-flash-lite"
)


def generate_ui_specification(
    user_description: str,
    er_model: dict,
    previous_specification: dict | None = None,
) -> UISpecification:

    context = {
        "user_description": user_description,
        "er_model": er_model,
        "previous_specification": previous_specification,
    }

    system_prompt = """
You are the UI planning engine for ERGen.

ERGen generates frontend applications from ER models
defined in XML.

Your task is to create a UI specification using:
1. The user's natural-language description.
2. The supplied ER model.
3. The previous UI specification, if provided.

IMPORTANT RULES:

1. The ER model is the source of truth for data entities.
2. Only reference entities that exist in the supplied ER model.
3. Do not invent entities, attributes, or relationships.
4. Support arbitrary ER models, not just Employee or Department.
5. Choose pages, views, components, and features that match
   the user's requirements.
6. Use the ER model to understand available entities and
   relationships.
7. Set a page's entity to the exact entity name when that
   page primarily manages one entity.
8. Set a component's entity to the exact entity name when
   that component uses a particular entity.
9. Use null when no single entity applies.
10. Use sensible, unique routes such as '/dashboard'
    or '/products'.
11. Use component types such as metric, table, form,
    card, and chart.
12. Use view patterns such as dashboard, crud, grid,
    and detail when appropriate.
13. Keep componentClass and componentFile null.
    Java will determine implementation details.
14. If a previous specification is supplied, use it to
    understand requested changes.
15. Do not generate source code. Return only the
    structured UI specification.
"""

    response = client.chat.completions.create(
        response_model=UISpecification,
        messages=[
            {
                "role": "system",
                "content": system_prompt,
            },
            {
                "role": "user",
                "content": json.dumps(context, indent=2)
            }
        ]
    )

    return response