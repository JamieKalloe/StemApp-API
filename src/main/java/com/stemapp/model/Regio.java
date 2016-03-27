package com.stemapp.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.stemapp.View;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Jamie on 27-3-2016.
 */
public class Regio {

    //Variables
    @NotEmpty
    @JsonView(View.Public.class)
    private String name;

    @JsonView(View.Private.class)
    private int id;

    //Constructor
    public Regio() {}

    public Regio(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Getters / Setters
    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
