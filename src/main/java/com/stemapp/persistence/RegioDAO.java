package com.stemapp.persistence;

import com.stemapp.database.Database;
import com.stemapp.model.Regio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jamie on 27-3-2016.
 */
public class RegioDAO implements DAO<Regio> {

    //Variables
    private final Database databaseInstance;

    public RegioDAO() {
        this.databaseInstance = Database.getInstance();
    }

    @Override
    public List<Regio> getAll() {
        return this.getAllFromDatabase();
    }

    public List<Regio> getAll(int vragenlijstId) {
        return this.getAllFromDatabaseFor(vragenlijstId);
    }

    @Override
    public Regio get(int id) {
        return this.getRegio(id);
    }

    public Regio getByName(String name) {
        return this.getRegio(name);
    }

    public boolean exists(String name) {

        Regio checkRegio = this.getByName(name);
        if(!checkRegio.getName().isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public void add(Regio regio) {
//        if(!exists(regio.getName())) {
            this.addRegioToDatabase(regio);
//        }
    }

    public void add(int id, Regio regio) {
        this.addRegioToDatabaseFor(id, regio);
    }

    @Override
    public void update(int id, Regio regio) {
        this.updateRegioFromDatabase(id, regio);
    }

    @Override
    public void delete(int id) {
        this.removeRegioFromDatabase(get(id));
    }

    private List<Regio> getAllFromDatabase() {
        List<Regio> regioList = new ArrayList<>();
        ResultSet results = databaseInstance.select("regio");

        try {
            while(results.next()) {
                Regio regio = new Regio();

                regio.setId(results.getInt("id"));
                regio.setName(results.getString("naam"));

                regioList.add(regio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return regioList;
    }

    private List<Regio> getAllFromDatabaseFor(int id) {
        List<Regio> regioList = new ArrayList<>();
        ResultSet results = databaseInstance.select("regio_vragenlijst", "vragenlijst_id=" + id);

        try {
            while(results.next()) {
                Regio regio = new Regio();

                //get from dao itself..
                regio.setId(results.getInt("regio_id"));
                regioList.add(regio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return regioList;
    }

    public Regio getRegio(int id) {
        ResultSet results = databaseInstance.select("regio", id);
        Regio regio = new Regio();

        try {
            if(results.next()) {
                regio.setId(results.getInt("id"));
                regio.setName(results.getString("naam"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return regio;
    }

    public Regio getRegio(String name) {
        ResultSet results = databaseInstance.select("regio", "naam=" + name);
        Regio regio = new Regio();

        try {
            if(results.next()) {
                regio.setId(results.getInt("id"));
                regio.setName(results.getString("naam"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return regio;
    }

    private Regio addRegioToDatabase(Regio regio) {
        HashMap databaseData = new HashMap();

        databaseData.put("naam", regio.getName());

        int id = databaseInstance.insertInto("regio", databaseData);
        regio.setId(id);
        return regio;
    }

    private Regio addRegioToDatabaseFor(int vragenlijstId, Regio regio) {
        HashMap databaseData = new HashMap();

        databaseData.put("vragenlijst_id", vragenlijstId);
        databaseData.put("regio_id", regio.getId());

        int id = databaseInstance.insertInto("regio_vragenlijst", databaseData);

        regio.setId(id);
        return regio;
    }

    private void updateRegioFromDatabase(int id, Regio regio) {
        HashMap databaseData = new HashMap();

        databaseData.put("naam", regio.getName());

        databaseInstance.update("regio", id, databaseData);
    }

    private void removeRegioFromDatabase(Regio regio) {
        databaseInstance.delete("regio", regio.getId());
    }
}
