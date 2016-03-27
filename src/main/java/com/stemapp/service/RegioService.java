package com.stemapp.service;

import com.stemapp.model.Regio;
import com.stemapp.persistence.RegioDAO;

import java.util.Collection;

/**
 * Created by Jamie on 27-3-2016.
 */
public class RegioService extends BaseService<Regio>{

    //Variables
    private final RegioDAO dao;

    public RegioService(RegioDAO dao) {
        this.dao = dao;
    }

    public Collection<Regio> getAll() {
        return dao.getAll();
    }

    public Regio get(int id) {
        return requireResult(dao.get(id));
    }

    public void add(Regio regio) {
        dao.add(regio);
    }

    public void update(int id, Regio regio) {
        //Check if it exists
        Regio checkRegio = get(id);
        
        dao.update(id, regio);
    }

    public void delete(int id) {
        //Check if it exists
        Regio checkRegio = get(id);

        dao.delete(id);
    }
}
