package com.stemapp.service;

import com.stemapp.model.Regio;
import com.stemapp.model.Vragenlijst;
import com.stemapp.persistence.*;

import java.util.Collection;

/**
 * Created by Jamie on 4-4-2016.
 */
public class VragenlijstService extends BaseService<Vragenlijst> {

    //Variables
    private final VragenlijstDAO vragenlijstDAO;
    private final CategorieDAO categorieDAO;
    private final StellingDAO stellingDAO;
    private final RegioDAO regioDAO;

    public VragenlijstService(VragenlijstDAO vragenlijstDAO, CategorieDAO categorieDAO, StellingDAO stellingDAO, RegioDAO regioDAO) {
        this.vragenlijstDAO = vragenlijstDAO;
        this.categorieDAO = categorieDAO;
        this.stellingDAO = stellingDAO;
        this.regioDAO = regioDAO;
    }

    public Collection<Vragenlijst> getAll() {

        Collection<Vragenlijst> vragenlijsten = vragenlijstDAO.getAll();
        for(Vragenlijst vragenlijst : vragenlijsten) {
            vragenlijst.setCategorie(categorieDAO.get(vragenlijst.getCategorie().getId()));
            vragenlijst.setStellingen(stellingDAO.getAll(vragenlijst.getId()));
            vragenlijst.setRegios(regioDAO.getAll(vragenlijst.getId()));

            for(Regio regio : vragenlijst.getRegios()) {
                vragenlijst.getRegios().set(vragenlijst.getRegios().indexOf(regio), regioDAO.get(regio.getId()));
            }
        }

        return vragenlijsten;
    }

    public Vragenlijst get(int id) {
        Vragenlijst vragenlijst = vragenlijstDAO.get(id);
        vragenlijst.setCategorie(categorieDAO.get(vragenlijst.getCategorie().getId()));
        vragenlijst.setStellingen(stellingDAO.getAll(vragenlijst.getId()));
        vragenlijst.setRegios(regioDAO.getAll(vragenlijst.getId()));

        for(Regio regio : vragenlijst.getRegios()) {
            vragenlijst.getRegios().set(vragenlijst.getRegios().indexOf(regio), regioDAO.get(regio.getId()));
        }

        return requireResult(vragenlijst);
    }

    public void add(Vragenlijst vragenlijst) {
        vragenlijstDAO.add(vragenlijst);
    }

    public void update(int id, Vragenlijst vragenlijst) {
        vragenlijstDAO.update(id, vragenlijst);
    }

    public void delete(int id) {
        vragenlijstDAO.delete(id);
    }
}
