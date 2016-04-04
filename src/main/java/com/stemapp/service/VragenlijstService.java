package com.stemapp.service;

import com.stemapp.model.Vragenlijst;
import com.stemapp.persistence.CategorieDAO;
import com.stemapp.persistence.StellingDAO;
import com.stemapp.persistence.VragenlijstDAO;

import java.util.Collection;

/**
 * Created by Jamie on 4-4-2016.
 */
public class VragenlijstService extends BaseService<Vragenlijst> {

    //Variables
    private final VragenlijstDAO vragenlijstDAO;
    private final CategorieDAO categorieDAO;
    private final StellingDAO stellingDAO;

    public VragenlijstService(VragenlijstDAO vragenlijstDAO, CategorieDAO categorieDAO, StellingDAO stellingDAO) {
        this.vragenlijstDAO = vragenlijstDAO;
        this.categorieDAO = categorieDAO;
        this.stellingDAO = stellingDAO;

    }

    public Collection<Vragenlijst> getAll() {

        Collection<Vragenlijst> vragenlijsten = vragenlijstDAO.getAll();
        for(Vragenlijst vragenlijst : vragenlijsten) {
            vragenlijst.setCategorie(categorieDAO.get(vragenlijst.getCategorie().getId()));
            vragenlijst.setStellingen(stellingDAO.get(vragenlijst));
        }

        return vragenlijsten;
    }

    public Vragenlijst get(int id) {
        Vragenlijst vragenlijst = vragenlijstDAO.get(id);
        vragenlijst.setCategorie(categorieDAO.get(vragenlijst.getCategorie().getId()));

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
