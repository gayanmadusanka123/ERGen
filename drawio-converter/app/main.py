from fastapi import FastAPI, HTTPException

from app.diagram_parser import parse_drawio_xml
from app.models import ConvertRequest

app = FastAPI(title="ERGen Draw.io Converte")

@app.post("/parse")
def parse_diagram(request: ConvertRequest):
    try:
        return parse_drawio_xml(request.drawio_xml)
    except Exception as exc:
        raise HTTPException(
            status_code=400,
            detail=f"Could not parse Draw.io XML: {exc}",
        )