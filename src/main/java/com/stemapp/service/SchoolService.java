package com.stemapp.service;

import com.stemapp.model.School;
import com.stemapp.persistence.RegioDAO;
import com.stemapp.persistence.SchoolDAO;

import java.util.Collection;
import java.util.List;

/**
 * Created by Jamie on 28-3-2016.
 */
public class SchoolService extends BaseService<School> implements Service<School> {

    //Variables
    private final SchoolDAO dao;
    private final RegioDAO regioDAO;

    public SchoolService(SchoolDAO dao, RegioDAO regioDAO) {
        this.dao = dao;
        this.regioDAO = regioDAO;
    }

    @Override
    public Collection<School> getAll() {
        Collection<School> schools = dao.getAll();

        for(School school : schools) {
            school.setRegio(regioDAO.get(school.getRegio().getId()));
        }

        return schools;
    }

    @Override
    public School get(int id) {
        School school = dao.get(id);
        school.setRegio(regioDAO.get(school.getRegio().getId()));

        return requireResult(school);
    }

    @Override
    public void add(School school) {
        dao.add(school);
    }

    @Override
    public void update(int id, School school) {
        dao.update(id, school);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
}
