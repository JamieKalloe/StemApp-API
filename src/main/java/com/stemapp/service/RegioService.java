package com.stemapp.service;

import com.stemapp.model.Regio;
import com.stemapp.persistence.RegioDAO;

/**
 * Created by Jamie on 27-3-2016.
 */
public class RegioService extends BaseService<Regio>{

    //Variables
    private final RegioDAO dao;

    public RegioService(RegioDAO dao) {
        this.dao = dao;
    }
}
