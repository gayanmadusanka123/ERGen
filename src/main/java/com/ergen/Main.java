package com.ergen;

import com.ergen.database.Postgres;
import com.ergen.generator.AngularGenerator;
import com.ergen.generator.CodeGenerator;
import com.ergen.model.Attribute;
import com.ergen.model.Entity;
import com.ergen.model.Relationship;
import com.ergen.parser.XmlParser;
import org.w3c.dom.Attr;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

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

            AngularGenerator angularGenerator = new AngularGenerator();
            angularGenerator.generate(entities);

            System.out.println("Code generation Completed");
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


