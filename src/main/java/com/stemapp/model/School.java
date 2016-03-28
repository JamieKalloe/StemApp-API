package com.stemapp.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.stemapp.View;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Jamie on 28-3-2016.
 */
public class School {

    //Variables
    @NotEmpty
    @JsonView(View.Public.class)
    private String name;

    @JsonView(View.Public.class)
    private Regio regio;

    @JsonView(View.Private.class)
    private int id;

    public School() {

    }

    public String getName() {
        return this.name;
    }

    public Regio getRegio() {
        return this.regio;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegio(Regio regio) {
        this.regio = regio;
    }

    public void setId(int id) {
        this.id = id;
    }
}
