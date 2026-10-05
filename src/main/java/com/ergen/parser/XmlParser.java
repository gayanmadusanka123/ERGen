package com.ergen.parser;

import com.ergen.database.Postgres;
import com.ergen.model.Attribute;
import com.ergen.model.Entity;
import com.ergen.model.Relationship;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    private final DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;

    public XmlParser() throws ParserConfigurationException, IOException, SAXException {
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(new File("input/model.xml"));
    }

    public List<Entity> getEntities(){
        List<Entity> entities = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName("entity");

        for (int i = 0; i < nodeList.getLength(); i++){
            Element entityElement = (Element) nodeList.item(i);
            String entityName = entityElement.getAttribute("name");

            List<Attribute> attributes = new ArrayList<>();
            NodeList attributeNodes = entityElement.getElementsByTagName("attribute");

            for (int j = 0; j < attributeNodes.getLength(); j++){
                Element attributeElement = (Element) attributeNodes.item(j);
                String name = attributeElement.getAttribute("name");
                String type = attributeElement.getAttribute("type");
                boolean primaryKey = Boolean.parseBoolean(attributeElement.getAttribute("primaryKey"));
                boolean required = Boolean.parseBoolean(attributeElement.getAttribute("required"));

                Attribute attribute =
                        new Attribute(
                                name,
                                type,
                                primaryKey,
                                required
                        );
                attributes.add(attribute);
            }

            Entity entity = new Entity(entityName, attributes);
            entities.add(entity);
        }

        List<Relationship> relationships = getRelationships();

        for(Entity entity: entities){
            for(Relationship relationship: relationships){
                if(relationship.getFrom().equals(entity.getName())
                    || relationship.getTo().equals(entity.getName())){
                    entity.getRelationships().add(relationship);
                }
            }
        }
        return entities;
    }

    public List<Relationship> getRelationships(){
        List<Relationship> relationships = new ArrayList<>();
        NodeList relationshipAttributes = document.getElementsByTagName("relationship");

        for (int i = 0; i < relationshipAttributes.getLength(); i++){
            Element relationshipElement = (Element) relationshipAttributes.item(i);
            String type = relationshipElement.getAttribute("type");
            String from = relationshipElement.getAttribute("from");
            String to = relationshipElement.getAttribute("to");
            String fieldName = relationshipElement.getAttribute("fieldName");
            String inverseFieldName = relationshipElement.getAttribute("inverseFieldName");

            Relationship relationship = new Relationship(
                    type,
                    from,
                    to,
                    fieldName,
                    inverseFieldName
            );
            relationships.add(relationship);
        }
        return relationships;
    }

    public List<Postgres> getDatabase(){
        List<Postgres> postgres = new ArrayList<>();
        NodeList databaseAttributes = document.getElementsByTagName("database");

        for (int i = 0; i < databaseAttributes.getLength(); i++){
            Element databaseElement = (Element) databaseAttributes.item(i);

            String name = databaseElement.getAttribute("name");
            String username = databaseElement.getAttribute("username");
            String password = databaseElement.getAttribute("password");
            String host = databaseElement.getAttribute("host");
            String port = databaseElement.getAttribute("port");

            Postgres postgre = new Postgres(
                    name,
                    username,
                    password,
                    host,
                    port
            );
            postgres.add(postgre);
        }
        return postgres;
    }
}
