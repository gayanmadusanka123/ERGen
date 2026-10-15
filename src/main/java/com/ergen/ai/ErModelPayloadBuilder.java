package com.ergen.ai;

import com.ergen.model.Attribute;
import com.ergen.model.Entity;
import com.ergen.model.Relationship;
import com.ergen.parser.XmlParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;


public class ErModelPayloadBuilder {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Object> buildErModel(XmlParser parser){
        Map<String, Object> erModel = new LinkedHashMap<>();
        List<Map<String, Object>> entityData = new ArrayList<>();

        for(Entity entity : parser.getEntities()){
            Map<String, Object> entityMap = new LinkedHashMap<>();
            entityMap.put("name", entity.getName());

            List<Map<String, Object>> attributeData = new ArrayList<>();

            for(Attribute attribute : entity.getAttributes()){
                Map<String, Object> attributeMap = new LinkedHashMap<>();

                attributeMap.put("name", attribute.getName());
                attributeMap.put("type", attribute.getType());
                attributeMap.put("primaryKey", attribute.isPrimaryKey());
                attributeMap.put("required", attribute.isRequired());

                attributeData.add(attributeMap);
            }

            entityMap.put("attributes", attributeData);
            entityData.add(entityMap);
        }

        //convert relationships into a separate list.
        List<Map<String, Object>> relationshipData = new ArrayList<>();

        for(Relationship relationship : parser.getRelationships()){
            Map<String, Object> relationshipMap = new LinkedHashMap<>();

            relationshipMap.put("type", relationship.getType());
            relationshipMap.put("from", relationship.getFrom());
            relationshipMap.put("to", relationship.getTo());
            relationshipMap.put("fieldName", relationship.getFieldName());
            relationshipMap.put("inverseFieldName", relationship.getInverseFieldName());

            relationshipData.add(relationshipMap);
        }

        erModel.put("entities", entityData);
        erModel.put("relationships", relationshipData);

        return erModel;
    }

    public String toJson(Map<String, Object> erModel) throws com.fasterxml.jackson.core.JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(erModel);
    }
}
