package com.ergen.ui;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UIComponent {
    private String type;
    private String entity;

    private List<String> features;

    public UIComponent(){
        features = new ArrayList<>();
    }
}
