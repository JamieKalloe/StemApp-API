package com.stemapp.service;

import com.stemapp.model.Stelling;
import com.stemapp.persistence.StellingDAO;

import java.util.Collection;

/**
 * Created by Jamie on 5-4-2016.
 */
public class StellingService extends BaseService<Stelling> {

    //Variables
    private final StellingDAO stellingDAO;

    public StellingService(StellingDAO stellingDAO) {
        this.stellingDAO = stellingDAO;
    }

    public Collection<Stelling> getAll() {
        return stellingDAO.getAll();
    }

    public Stelling get(int id) {
        return requireResult(stellingDAO.get(id));
    }

    public void add(Stelling stelling) {
        stellingDAO.add(stelling);
    }

    public void update(int id, Stelling stelling) {
        stellingDAO.update(id, stelling);
    }

    public void delete(int id) {
        stellingDAO.delete(id);
    }



}
