package com.ergen.ai;

import com.ergen.parser.XmlParser;
import com.ergen.ui.UISpecification;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class AIPlannerTest {

    public static void main(String[] args) throws Exception {

        // Read the ER XML using your existing parser.
        XmlParser parser = new XmlParser();

        // Convert the parsed ER model into the JSON structure.
        ErModelPayloadBuilder payloadBuilder =
                new ErModelPayloadBuilder();

        Map<String, Object> erModel =
                payloadBuilder.buildErModel(parser);

        // Ask the user to describe the desired frontend.
        String description =
                "Create a dashboard with useful metrics and CRUD pages "
                        + "for the entities in this system.";

        // Call the Python AI planner.
        AIPlannerClient client = new AIPlannerClient();

        JsonNode response = client.generateSpecification(
                description,
                erModel,
                null
        );

        //convert AI Json response into a java object
        ObjectMapper objectMapper = new ObjectMapper();

        UISpecification specification = objectMapper.treeToValue(
                response,
                UISpecification.class
        );

        //print
        System.out.println("\nAI-generated UI specification: ");
        System.out.println(
                objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(specification)
        );

    }
}
