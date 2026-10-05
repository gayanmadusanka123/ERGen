package com.ergen.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Relationship {
    private String type;
    private String from;
    private String to;
    private String fieldName;
    private String inverseFieldName;

    public Relationship(String type, String from, String to, String fieldName, String inverseFieldName) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.fieldName = fieldName;
        this.inverseFieldName = inverseFieldName;
    }
}
