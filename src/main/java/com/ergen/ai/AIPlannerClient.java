
package com.ergen.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class AIPlannerClient {

    private final ObjectMapper objectMapper;
    private final String plannerUrl;

    public AIPlannerClient() {
        this("http://127.0.0.1:8000/plan");
    }

    public AIPlannerClient(String plannerUrl) {
        this.plannerUrl = plannerUrl;
        this.objectMapper = new ObjectMapper();
    }

    public JsonNode generateSpecification(
            String userDescription,
            Map<String, Object> erModel,
            Map<String, Object> previousSpecification
    ) throws IOException {

        // 1. Build the request expected by FastAPI.
        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("user_description", userDescription);
        requestBody.put("er_model", erModel);
        requestBody.put("previous_specification", previousSpecification);

        // 2. Convert the request into JSON bytes.
        String requestJson = objectMapper.writeValueAsString(requestBody);
        byte[] requestBytes = requestJson.getBytes(StandardCharsets.UTF_8);

        System.out.println("Sending request to: " + plannerUrl);
        System.out.println("Request body bytes: " + requestBytes.length);

        HttpURLConnection connection =
                (HttpURLConnection) new URL(plannerUrl).openConnection();

        try {
            // 3. Configure the HTTP POST request.
            connection.setRequestMethod("POST");
            connection.setRequestProperty(
                    "Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(10_000);
            connection.setReadTimeout(120_000);
            connection.setFixedLengthStreamingMode(requestBytes.length);

            // 4. Write the request body.
            try (OutputStream output = connection.getOutputStream()) {
                output.write(requestBytes);
            }

            // 5. Read the server's response.
            int status = connection.getResponseCode();

            InputStream responseStream =
                    status >= 400
                            ? connection.getErrorStream()
                            : connection.getInputStream();

            String responseBody = "";

            if (responseStream != null) {
                try (InputStream input = responseStream) {
                    responseBody =
                            new String(input.readAllBytes(), StandardCharsets.UTF_8);
                }
            }

            System.out.println("HTTP status: " + status);
            System.out.println("Response body: " + responseBody);

            // 6. Reject unsuccessful responses.
            if (status < 200 || status >= 300) {
                throw new IOException(
                        "AI planner returned HTTP " + status + ": " + responseBody
                );
            }

            // 7. Convert the response JSON into a JsonNode.
            return objectMapper.readTree(responseBody);

        } finally {
            connection.disconnect();
        }
    }
}
