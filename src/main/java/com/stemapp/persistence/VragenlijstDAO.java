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
    private final Database databaseInstance;

    public VragenlijstDAO() {
        this.databaseInstance = Database.getInstance();
    }

    @Override
    public List<Vragenlijst> getAll() {
        return this.getAllFromDatabase();
    }

    @Override
    public Vragenlijst get(int id) {
        return this.getVragenlijst(id);
    }

    @Override
    public void add(Vragenlijst vragenlijst) {
        this.addVragenlijstToDatabase(vragenlijst);
    }

    @Override
    public void update(int id, Vragenlijst vragenlijst) {
        this.updateVragenlijstFromDatabase(vragenlijst, id);
    }

    @Override
    public void delete(int id) {
        this.removeVragenlijstFromDatabase(get(id));
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

    private Vragenlijst getVragenlijst(int id) {
        ResultSet results = databaseInstance.select("vragenlijst", id);
        Vragenlijst vragenlijst = new Vragenlijst();

        try {
            if(results.next()) {
                vragenlijst.setId(results.getInt("id"));
                vragenlijst.setNaam(results.getString("naam"));
                vragenlijst.setCategorie(new Categorie(results.getInt("categorie_id")));
                vragenlijst.setStatus(results.getBoolean("status"));
                vragenlijst.setStudentStartDatum(results.getDate("start_datum_student"));
                vragenlijst.setStudentEindDatum(results.getDate("eind_datum_student"));
                vragenlijst.setPoliticiStartDatum(results.getDate("start_datum_politici"));
                vragenlijst.setPoliticiEindDatum(results.getDate("eind_datum_politici"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return vragenlijst;
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

    private void updateVragenlijstFromDatabase(Vragenlijst vragenlijst, int id) {
        HashMap databaseData = new HashMap();

        databaseData.put("naam", vragenlijst.getNaam());
        databaseData.put("categorie_id", vragenlijst.getCategorie().getId());
        databaseData.put("status", vragenlijst.getStatus());
        databaseData.put("start_datum_student", vragenlijst.getStudentStartDatum().toString());
        databaseData.put("eind_datum_student", vragenlijst.getStudentEindDatum().toString());
        databaseData.put("start_datum_politici", vragenlijst.getPoliticiStartDatum().toString());
        databaseData.put("eind_datum_politici", vragenlijst.getPoliticiEindDatum().toString());

        databaseInstance.update("vragenlijst", id, databaseData);
    }

    private void removeVragenlijstFromDatabase(Vragenlijst vragenlijst) {
        databaseInstance.delete("vragenlijst", vragenlijst.getId());
    }
}
