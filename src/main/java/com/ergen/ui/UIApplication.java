package com.ergen.ui;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UIApplication {
    private String name;
    private String layout;

    private List<UIPage> pages;

    public UIApplication(){
        pages = new ArrayList<>();
    }
}
