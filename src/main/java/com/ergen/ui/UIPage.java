package com.ergen.ui;

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

    public UIPage(){
        components = new ArrayList<>();
    }
}
