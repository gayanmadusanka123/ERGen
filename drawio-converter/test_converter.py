
import json
from urllib.request import Request, urlopen

# Read the original Draw.io XML without manually editing it.
with open("drawio.xml", "r", encoding="utf-8") as file:
    drawio_xml = file.read()

# Build the JSON request. json.dumps escapes the XML safely.
body = json.dumps({
    "drawio_xml": drawio_xml
}).encode("utf-8")

request = Request(
    "http://127.0.0.1:8001/parse",
    data=body,
    headers={"Content-Type": "application/json"},
    method="POST",
)

# Send the request and print the response.
with urlopen(request) as response:
    print("HTTP status:", response.status)
    print(json.dumps(
        json.loads(response.read().decode("utf-8")),
        indent=2
    ))
