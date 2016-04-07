package com.stemapp.service;

import com.stemapp.model.Categorie;
import com.stemapp.persistence.CategorieDAO;

import java.util.Collection;

/**
 * Created by Jamie on 1-4-2016.
 */
public class CategorieService extends BaseService<Categorie> implements Service<Categorie> {

    //Variables
    private final CategorieDAO dao;

    public CategorieService(CategorieDAO dao) {
        this.dao = dao;
    }

    @Override
    public Collection<Categorie> getAll() {
        return dao.getAll();
    }

    @Override
    public Categorie get(int id) {
        return requireResult(dao.get(id));
    }

    @Override
    public void add(Categorie categorie) {
        dao.add(categorie);
    }

    @Override
    public void update(int id, Categorie categorie) {
        dao.update(id, categorie);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
}
