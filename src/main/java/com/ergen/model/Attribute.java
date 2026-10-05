package com.ergen.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attribute {
    private String name;
    private String type;
    private boolean primaryKey;
    private boolean required;

    public Attribute(String name, String type, boolean primaryKey, boolean required){
        this.name = name;
        this.type = type;
        this.primaryKey = primaryKey;
        this.required = required;
    }

    public String getTypeScriptType(){
        switch (type){
            case "String":
                return "string";

            case "Integer":
            case "Long":
            case "Double":
            case "Float":
                return "number";

            case "Boolean":
                return "boolean";

            default:
                return "any";
        }
    }
}
