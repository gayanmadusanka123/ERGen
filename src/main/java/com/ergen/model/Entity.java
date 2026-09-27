package com.ergen.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Entity {
    private String name;
    private List<Attribute> attributes = new ArrayList<>();
    private List<Relationship> relationships = new ArrayList<>();

    public Entity(String name, List<Attribute> attributes){
        this.name = name;
        this.attributes = attributes;
    }

    public Attribute getPrimaryKey(){
        for(Attribute attribute : attributes){
            if(attribute.isPrimaryKey()){
                return attribute;
            }
        }
        return null;
    }
}
