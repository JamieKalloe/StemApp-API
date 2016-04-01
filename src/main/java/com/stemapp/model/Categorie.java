package com.stemapp.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.stemapp.View;

import javax.validation.constraints.NotNull;

/**
 * Created by Jamie on 1-4-2016.
 */
public class Categorie {

    @JsonView(View.Public.class)
    private int id;

    @NotNull
    @JsonView(View.Public.class)
    private String name;

    public Categorie() {}

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
