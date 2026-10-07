package com.ergen.ui;

import com.ergen.model.Relationship;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UIPage {
    private String name;
    private String entity;
    private String route;
    private String view;

    private String componentClass;
    private String componentFile;

    private List<UIComponent> components;

    private List<Relationship> relationships;

    public List<Relationship> getRelationships() {
        return relationships;
    }
    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

    public UIPage(){
        components = new ArrayList<>();
        relationships = new ArrayList<>();
    }

}
