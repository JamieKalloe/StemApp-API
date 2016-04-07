package com.stemapp.service;

import com.stemapp.model.School;
import com.stemapp.persistence.RegioDAO;
import com.stemapp.persistence.SchoolDAO;

import java.util.Collection;

/**
 * Created by Jamie on 28-3-2016.
 */
public class SchoolService extends BaseService<School> implements Service<School> {

    //Variables
    private final SchoolDAO dao;

    public SchoolService(SchoolDAO dao) {
        this.dao = dao;
    }

    @Override
    public Collection<School> getAll() {
        return dao.getAll();
    }

    @Override
    public School get(int id) {
//        //Get the school from the DAO, throws exception if not found.
//        School school = requireResult(dao.get(id));
//
//        //Get the Regio data from the RegioDAO, given the RegioId from the School model.
//        school.setRegio(new RegioDAO().get(school.getRegio().getId()));
        return requireResult(dao.get(id));
    }

    @Override
    public void add(School school) {
        dao.add(school);
    }

    @Override
    public void update(int id, School school) {
        //Check if the school exists
        School checkSchool = get(id);

        dao.update(id, school);
    }

    @Override
    public void delete(int id) {
        //Check if the school exists
        School checkSchool = get(id);

        dao.delete(id);
    }
}
