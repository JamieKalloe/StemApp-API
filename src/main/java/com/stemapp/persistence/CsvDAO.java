package com.stemapp.persistence;

import com.opencsv.CSVReader;
import com.stemapp.model.Regio;
import com.stemapp.model.School;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jamie on 28-3-2016.
 */
public class CsvDAO {

    private final RegioDAO regioDAO;
    private final SchoolDAO schoolDAO;

    public CsvDAO(RegioDAO regioDAO, SchoolDAO schoolDAO) {
        this.regioDAO = regioDAO;
        this.schoolDAO = schoolDAO;
    }

    private List<String[]> readCSV(File file) {
        List<String[]> allRows = null;

        try {
            CSVReader reader = new CSVReader(new FileReader(file), ';', '"', 1);
            allRows = reader.readAll();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return allRows;
    }

    public void importMiddleSchool(File file) {
        List<String[]> middleSchoolData = this.readCSV(file);
        ArrayList<School> schoolList = new ArrayList<>();

        //Get all regio's
        if(middleSchoolData != null) {
            for(int i = 0; i < middleSchoolData.size(); i++) {
                Regio regio = new Regio();
                regio.setName(middleSchoolData.get(i)[30].replace("'", "''"));

                School school = new School();
                school.setName(middleSchoolData.get(i)[4].replace("'", "''"));
                school.setRegio(regio);
                schoolList.add(school);

                regioDAO.add(regio);
            }

            System.out.println("Imported " + middleSchoolData.size() + " regio's ");

            for(School school : schoolList) {
                school.setRegio(regioDAO.getByName(school.getRegio().getName()));

                schoolDAO.add(school);
            }
            System.out.println("Imported " + schoolList.size() + " schools");
        }
    }

    public void importMBO(File file) {
        System.out.println();
    }
}
