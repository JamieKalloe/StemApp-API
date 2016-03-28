package com.stemapp.service;

import com.stemapp.persistence.CsvDAO;

import java.io.File;

/**
 * Created by Jamie on 28-3-2016.
 */
public class CsvService {

    private final CsvDAO dao;

    public CsvService(CsvDAO dao) {
        this.dao = dao;
    }

    public void importMiddleSchool(File file) {
        dao.importMiddleSchool(file);
    }

    public void importMBO(File file) {
        dao.importMBO(file);
    }
}
