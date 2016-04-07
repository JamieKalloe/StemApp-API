package com.stemapp.persistence;

import com.stemapp.database.Database;
import com.stemapp.model.Categorie;
import com.stemapp.model.Vragenlijst;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jamie on 4-4-2016.
 */
public class VragenlijstDAO implements DAO<Vragenlijst> {

    //Variables
    private List<Vragenlijst> vragenlijsten;
    private final Database databaseInstance;

    public VragenlijstDAO() {
        this.databaseInstance = Database.getInstance();
        this.vragenlijsten = this.getAllFromDatabase();
    }

    @Override
    public List<Vragenlijst> getAll() {
        return this.vragenlijsten;
    }

    @Override
    public Vragenlijst get(int id) {
        try {
            for(Vragenlijst vragenlijst : vragenlijsten) {
                if(vragenlijst.getId() == id) {
                    return vragenlijst;
                }
            }
            return null;

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(Vragenlijst vragenlijst) {
        vragenlijst = this.addVragenlijstToDatabase(vragenlijst);
        vragenlijsten.add(vragenlijst);
    }

    @Override
    public void update(int id, Vragenlijst vragenlijst) {
        Vragenlijst oldVragenlijst = this.get(id);
        vragenlijst.setId(id);

        this.updateVragenlijstFromDatabase(vragenlijst);
        int idInList = vragenlijsten.indexOf(oldVragenlijst);
        vragenlijsten.set(idInList, vragenlijst);
    }

    @Override
    public void delete(int id) {
        Vragenlijst vragenlijst = this.get(id);
        this.removeVragenlijstFromDatabase(vragenlijst);
        vragenlijsten.remove(vragenlijst);
    }

    private List<Vragenlijst> getAllFromDatabase() {
        List<Vragenlijst> vragenlijstList = new ArrayList<>();
        ResultSet results = databaseInstance.select("vragenlijst");

        try {
            while(results.next()) {
                Vragenlijst vragenlijst = new Vragenlijst();

                vragenlijst.setId(results.getInt("id"));
                vragenlijst.setNaam(results.getString("naam"));
                vragenlijst.setCategorie(new Categorie(results.getInt("categorie_id")));
                vragenlijst.setStatus(results.getBoolean("status"));
                vragenlijst.setStudentStartDatum(results.getDate("start_datum_student"));
                vragenlijst.setStudentEindDatum(results.getDate("eind_datum_student"));
                vragenlijst.setPoliticiStartDatum(results.getDate("start_datum_politici"));
                vragenlijst.setPoliticiEindDatum(results.getDate("eind_datum_politici"));

                vragenlijstList.add(vragenlijst);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return vragenlijstList;
    }

    private Vragenlijst addVragenlijstToDatabase(Vragenlijst vragenlijst) {
        HashMap databaseData = new HashMap();

        databaseData.put("naam", vragenlijst.getNaam());
        databaseData.put("categorie_id", vragenlijst.getCategorie().getId());
        databaseData.put("status", vragenlijst.getStatus());
        databaseData.put("start_datum_student", vragenlijst.getStudentStartDatum().toString());
        databaseData.put("eind_datum_student", vragenlijst.getStudentEindDatum().toString());
        databaseData.put("start_datum_politici", vragenlijst.getPoliticiStartDatum().toString());
        databaseData.put("eind_datum_politici", vragenlijst.getPoliticiEindDatum().toString());

        int id = databaseInstance.insertInto("vragenlijst", databaseData);
        vragenlijst.setId(id);

        return vragenlijst;
    }

    private void updateVragenlijstFromDatabase(Vragenlijst vragenlijst) {
        HashMap databaseData = new HashMap();

        databaseData.put("naam", vragenlijst.getNaam());
        databaseData.put("categorie_id", vragenlijst.getCategorie().getId());
        databaseData.put("status", vragenlijst.getStatus());
        databaseData.put("start_datum_student", vragenlijst.getStudentStartDatum().toString());
        databaseData.put("eind_datum_student", vragenlijst.getStudentEindDatum().toString());
        databaseData.put("start_datum_politici", vragenlijst.getPoliticiStartDatum().toString());
        databaseData.put("eind_datum_politici", vragenlijst.getPoliticiEindDatum().toString());

        databaseInstance.update("vragenlijst", vragenlijst.getId(), databaseData);
    }

    private void removeVragenlijstFromDatabase(Vragenlijst vragenlijst) {
        databaseInstance.delete("vragenlijst", vragenlijst.getId());
    }
}
