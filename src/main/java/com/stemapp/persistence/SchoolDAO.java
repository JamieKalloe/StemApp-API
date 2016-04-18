package com.stemapp.persistence;

import com.stemapp.database.Database;
import com.stemapp.model.Regio;
import com.stemapp.model.School;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jamie on 28-3-2016.
 */
public class SchoolDAO implements DAO<School> {

    //Variables
    private final Database databaseInstance;

    public SchoolDAO(RegioDAO regioDAO) {
        this.databaseInstance = Database.getInstance();
    }

    @Override
    public List<School> getAll() {
        return this.getAllFromDatabase();
    }

    @Override
    public School get(int id) {
        return this.getSchool(id);
    }

    public boolean exists(School checkSchool) {
        return true;
    }

    @Override
    public void add(School school) {
//        if(!exists(school)) {
            this.addSchoolToDatabase(school);
//        }
    }

    @Override
    public void update(int id, School school) {
        this.updateSchoolFromDatabase(id, school);
    }

    @Override
    public void delete(int id) {
        this.removeSchoolFromDatabase(get(id));
    }

    private List<School> getAllFromDatabase() {
        List<School> schoolList = new ArrayList<>();
        ResultSet results = databaseInstance.select("school");

        try {
            while(results.next()) {
                School school = new School();

                school.setId(results.getInt("id"));
                school.setName(results.getString("naam"));
                school.setRegio(new Regio(results.getInt("regio_id")));

                schoolList.add(school);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return schoolList;
    }

    private School getSchool(int id) {
        ResultSet results = databaseInstance.select("school", id);
        School school = new School();

        try {
            if(results.next()) {
                school.setId(results.getInt("id"));
                school.setName(results.getString("naam"));
                school.setRegio(new Regio(results.getInt("regio_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return school;
    }

    private School addSchoolToDatabase(School school) {
        HashMap databaseData = new HashMap();

        databaseData.put("regio_id", school.getRegio().getId());
        databaseData.put("naam", school.getName());

        int id = databaseInstance.insertInto("school", databaseData);
        school.setId(id);

        return school;
    }

    private void updateSchoolFromDatabase(int id, School school) {
        HashMap databaseData = new HashMap();

        databaseData.put("regio_id", school.getRegio().getId());
        databaseData.put("naam", school.getName());

        databaseInstance.update("school", id, databaseData);
    }

    private void removeSchoolFromDatabase(School school) {
        databaseInstance.delete("school", school.getId());
    }
}
