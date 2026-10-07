package com.ergen.ui;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UISpecification {
    private String name;
    private String layout;

    private List<UIPage> pages;

    public UISpecification(){
        pages = new ArrayList<>();
    }
}
