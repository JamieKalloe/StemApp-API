package com.stemapp.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.stemapp.View;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

/**
 * Created by Jamie on 4-4-2016.
 */
public class Vragenlijst extends Model {

    //Variables
    @JsonView(View.Public.class)
    private int id;

    @JsonView(View.Public.class)
    private String naam;

    @JsonView(View.Public.class)
    private Categorie categorie;

    @JsonView(View.Public.class)
    private Date studentStartDatum, studentEindDatum, politiciStartDatum, politiciEindDatum;

    @JsonView(View.Public.class)
    private boolean status;

    @JsonView(View.Public.class)
    private List<Stelling> stellingen;

    @JsonView(View.Public.class)
    private List<Regio> regios;

    public int getId() {
        return this.id;
    }

    public String getNaam() {
        return this.naam;
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

    public List<Stelling> getStellingen() {
        return this.stellingen;
    }

    public List<Regio> getRegios() {
        return this.regios;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNaam(String naam) {
        this.naam = naam;
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

    public void setStellingen(List<Stelling> stellingen) {
        this.stellingen = stellingen;
    }

    public void setRegios(List<Regio> regios) {
        this.regios = regios;
    }

}
