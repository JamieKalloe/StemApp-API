package com.stemapp.service;

import com.stemapp.model.Categorie;
import com.stemapp.persistence.CategorieDAO;

import java.util.Collection;

/**
 * Created by Jamie on 1-4-2016.
 */
public class CategorieService extends BaseService<Categorie> {

    //Variables
    private final CategorieDAO dao;

    public CategorieService(CategorieDAO dao) {
        this.dao = dao;
    }

    public Collection<Categorie> getAll() {
        return dao.getAll();
    }

    public Categorie get(int id) {
        return requireResult(dao.get(id));
    }

    public void add(Categorie categorie) {
        dao.add(categorie);
    }

    public void update(int id, Categorie categorie) {
        dao.update(id, categorie);
    }

    public void delete(int id) {
        dao.delete(id);
    }
}
