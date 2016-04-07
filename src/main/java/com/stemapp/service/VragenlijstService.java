package com.stemapp.service;

import com.stemapp.model.Regio;
import com.stemapp.model.Vragenlijst;
import com.stemapp.persistence.CategorieDAO;
import com.stemapp.persistence.RegioDAO;
import com.stemapp.persistence.StellingDAO;
import com.stemapp.persistence.VragenlijstDAO;

import java.util.Collection;

/**
 * Created by Jamie on 4-4-2016.
 */
public class VragenlijstService extends BaseService<Vragenlijst> implements Service<Vragenlijst> {

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

    @Override
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

    @Override
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

    @Override
    public void add(Vragenlijst vragenlijst) {
        vragenlijstDAO.add(vragenlijst);
    }

    @Override
    public void update(int id, Vragenlijst vragenlijst) {
        vragenlijstDAO.update(id, vragenlijst);
    }

    @Override
    public void delete(int id) {
        vragenlijstDAO.delete(id);
    }
}
