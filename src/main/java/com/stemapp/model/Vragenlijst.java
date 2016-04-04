package com.stemapp.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.stemapp.View;

import java.util.Date;

/**
 * Created by Jamie on 4-4-2016.
 */
public class Vragenlijst {

    //Variables
    @JsonView(View.Public.class)
    private int id;

    @JsonView(View.Public.class)
    private Categorie categorie;

    @JsonView(View.Public.class)
    private Date studentStartDatum, studentEindDatum, politiciStartDatum, politiciEindDatum;

    @JsonView(View.Public.class)
    private boolean status;

    public int getId() {
        return this.id;
    }

    public Categorie getCategorie() {
        return this.categorie;
    }

    public boolean getStatus() {
        return this.status;
    }

    public Date getStudentStartDatum() {
        return this.studentStartDatum;
    }

    public Date getStudentEindDatum() {
        return this.studentEindDatum;
    }

    public Date getPoliticiStartDatum() {
        return this.politiciStartDatum;
    }

    public Date getPoliticiEindDatum() {
        return this.politiciEindDatum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setStudentStartDatum(Date studentStartDatum) {
        this.studentStartDatum = studentStartDatum;
    }

    public void setStudentEindDatum(Date studentEindDatum) {
        this.studentEindDatum = studentEindDatum;
    }

    public void setPoliticiStartDatum(Date politiciStartDatum) {
        this.politiciStartDatum = politiciStartDatum;
    }

    public void setPoliticiEindDatum(Date politiciEindDatum) {
        this.politiciEindDatum = politiciEindDatum;
    }
}
