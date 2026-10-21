from urllib.request import Request, urlopen
from urllib.error import HTTPError
import json

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

try:
    with urlopen(request) as response:
        result = json.loads(response.read().decode("utf-8"))
        print(json.dumps(result, indent=4, ensure_ascii=False))

except HTTPError as exc:
    print("HTTP status:", exc.code)
    print("Error response:")
    print(exc.read().decode("utf-8"))

