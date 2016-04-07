package com.stemapp.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.stemapp.View;

/**
 * Created by Jamie on 4-4-2016.
 */
public class Stelling extends Model {

    @JsonView(View.Public.class)
    private int id, vragenlijstId;

    @JsonView(View.Public.class)
    private String titel, stelling, uitleg;

    public Stelling() {

    }

    public Stelling(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getVragenlijstId() {
        return this.vragenlijstId;
    }

    public String getTitel() {
        return this.titel;
    }

    public String getStelling() {
        return this.stelling;
    }

    public String getUitleg() {
        return this.uitleg;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVragenlijstId(int vragenlijstId) {
        this.vragenlijstId = vragenlijstId;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setStelling(String stelling) {
        this.stelling = stelling;
    }

    public void setUitleg(String uitleg) {
        this.uitleg = uitleg;
    }
}
