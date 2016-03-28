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
public class SchoolDAO {

    //Variables
    private List<School> schools;
    private final Database databaseInstance;
    private final RegioDAO regioDAO;

    public SchoolDAO(RegioDAO regioDAO) {
        this.databaseInstance = Database.getInstance();
        this.regioDAO = regioDAO;
        this.schools = getAllFromDatabase();
    }

    public List<School> getAll() {
        return this.schools;
    }

    public School get(int id) {
        try {
            for(School school : schools) {
                if(school.getId() == id) {
                    return school;
                }
            }
            return null;

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean exists(School checkSchool) {
        try {
            for(School school : schools) {
                if(school.getName().toLowerCase().equals(checkSchool.getName().toLowerCase())) {
                    return true;
                }
            }
            return false;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void add(School school) {
        if(!exists(school)) {
            school = this.addSchoolToDatabase(school);
            school.setRegio(regioDAO.get(school.getRegio().getId()));
            schools.add(school);
        }
    }

    public void update(int id, School school) {
        School oldSchool = this.get(id);
        school.setId(id);

        this.updateSchoolFromDatabase(school);
        int idInList = schools.indexOf(oldSchool);

        school.setRegio(regioDAO.get(school.getRegio().getId()));
        schools.set(idInList, school);
    }

    public void delete(int id) {
        School school = this.get(id);
        this.removeSchoolFromDatabase(school);
        schools.remove(school);
    }

    private List<School> getAllFromDatabase() {
        List<School> schoolList = new ArrayList<>();
        ResultSet results = databaseInstance.select("school");

        try {
            while(results.next()) {
                School school = new School();

                school.setId(results.getInt("id"));
                school.setName(results.getString("naam"));
//                school.setRegio(new Regio(results.getInt("regio_id")));
                school.setRegio(regioDAO.get(results.getInt("regio_id")));

                schoolList.add(school);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return schoolList;
    }

    private School addSchoolToDatabase(School school) {
        HashMap databaseData = new HashMap();

        databaseData.put("regio_id", school.getRegio().getId());
        databaseData.put("naam", school.getName());

        int id = databaseInstance.insertInto("school", databaseData);
        school.setId(id);

        return school;
    }

    private void updateSchoolFromDatabase(School school) {
        HashMap databaseData = new HashMap();

        databaseData.put("regio_id", school.getRegio().getId());
        databaseData.put("naam", school.getName());

        databaseInstance.update("school", school.getId(), databaseData);
    }

    private void removeSchoolFromDatabase(School school) {
        databaseInstance.delete("school", school.getId());
    }
}
