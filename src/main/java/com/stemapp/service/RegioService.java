package com.stemapp.service;

import com.stemapp.model.Categorie;
import com.stemapp.model.Regio;
import com.stemapp.persistence.RegioDAO;

import java.util.Collection;

/**
 * Created by Jamie on 27-3-2016.
 */
public class RegioService extends BaseService<Regio> implements Service<Regio> {

    //Variables
    private final RegioDAO dao;

    public RegioService(RegioDAO dao) {
        this.dao = dao;
    }

    @Override
    public Collection<Regio> getAll() {
        return dao.getAll();
    }

    @Override
    public Regio get(int id) {
        return requireResult(dao.get(id));
    }

    @Override
    public void add(Regio regio) {
        dao.add(regio);
    }

    public void add(int vragenlijstId, Regio regio) {
        dao.add(vragenlijstId, regio);
    }

    @Override
    public void update(int id, Regio regio) {
        dao.update(id, regio);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
}
