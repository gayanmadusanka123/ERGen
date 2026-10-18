package com.ergen;

import com.ergen.ai.AIPlannerClient;
import com.ergen.ai.ErModelPayloadBuilder;
import com.ergen.database.Postgres;
import com.ergen.generator.AngularGenerator;
import com.ergen.generator.CodeGenerator;
import com.ergen.model.Entity;
import com.ergen.model.Relationship;
import com.ergen.parser.XmlParser;
import com.ergen.ui.UISpecification;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        try{
            XmlParser parser = new XmlParser();
            List<Entity> entities = parser.getEntities();
            List<Relationship> relationships = parser.getRelationships();
            List<Postgres> postgres = parser.getDatabase();

            CodeGenerator generator = new CodeGenerator();
            generator.generateEntities(entities);

            generator.generateRepositeries(entities);
            generator.generateServices(entities);
            generator.generateControllers(entities);
            generator.generateMainClass();

            generator.generatePOMXML();
            generator.generateApplicationProperties(parser);
            generator.generateDockerFile(parser);

            /*
            AngularGenerator angularGenerator = new AngularGenerator();
            angularGenerator.generate(entities);*/

            ErModelPayloadBuilder payloadBuilder = new ErModelPayloadBuilder();

            Map<String, Object> erModel = payloadBuilder.buildErModel(parser);

            String description = "Create CRUD table pages with search for the entities in this system. Donot create a Desk page";

            AIPlannerClient aiPlanner = new AIPlannerClient();

            JsonNode aiResponse = aiPlanner.generateSpecification(
                    description,
                    erModel,
                    null
            );

            ObjectMapper objectMapper = new ObjectMapper();

            UISpecification specification = objectMapper.treeToValue(
                    aiResponse,
                    UISpecification.class
            );

            AngularGenerator angularGenerator = new AngularGenerator();
            angularGenerator.generate(entities, specification);



            System.out.println("Code generation Completed");

            /*
            ErModelPayloadBuilder payLoadBuilder = new ErModelPayloadBuilder();
            Map<String, Object> erModel = payLoadBuilder.buildErModel(parser);
            System.out.println(payLoadBuilder.toJson(erModel));
            */

            /*
            //print entities and their attributes
            for(Entity entity : entities){
                System.out.println("Entity: " + entity.getName());

                for (Attribute attribute : entity.getAttributes()){
                    System.out.println(
                            " Attribute: " + attribute.getName()
                            + " | Type: " + attribute.getType()
                            + " | Primary Key: " + attribute.isPrimaryKey()
                            + " | Required: " + attribute.isRequired()
                    );
                }
                System.out.println();
                for (Entity entity : entities) {

    System.out.println("ENTITY: " + entity.getName());

    Attribute primaryKey = entity.getPrimaryKey();

    if (primaryKey == null) {
        System.out.println("❌ PRIMARY KEY IS NULL");
    } else {
        System.out.println(
            "✅ PRIMARY KEY: "
            + primaryKey.getName()
            + " / "
            + primaryKey.getType()
        );

    }

    // your existing generation code...
}
            }
            System.out.println("Relationships: \n");
            for (Relationship relationship : relationships){
                System.out.println(
                        "Type: " + relationship.getType()
                        + "\nFrom: " + relationship.getFrom()
                        + "\nTo: " + relationship.getTo()

                );
                System.out.println();
            }*/
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}


