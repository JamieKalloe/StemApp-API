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
public class RegioDAO {

    //Variables
    private List<Regio> regios;
    private final Database databaseInstance;

    public RegioDAO() {
        this.databaseInstance = Database.getInstance();
        regios = this.getAllFromDatabase();
    }

    public List<Regio> getAll() {
        return this.regios;
    }

    public Regio get(int id) {
        try {
            for(Regio regio : regios) {
                if(regio.getId() == id) {
                    return regio;
                }
            }
            return null;

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Regio getByName(String name) {
        try {
            for(Regio regio : regios) {
                if(regio.getName().toLowerCase().equals(name.toLowerCase())) {
                    return regio;
                }
            }
            return null;

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean exists(Regio checkRegio) {
        try {
            for(Regio regio : regios) {
                if(regio.getName().toLowerCase().equals(checkRegio.getName().toLowerCase())) {
                    return true;
                }
            }
            return false;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void add(Regio regio) {
        if(!exists(regio)) {
            regio = this.addRegioToDatabase(regio);
            regios.add(regio);
        }
    }

    public void update(int id, Regio regio) {
        Regio oldRegio = this.get(id);
        regio.setId(id);

        this.updateRegioFromDatabase(regio);
        int idInList = regios.indexOf(oldRegio);
        regios.set(idInList, regio);
    }

    public void delete(int id) {
        Regio regio = this.get(id);
        this.removeRegioFromDatabase(regio);
        regios.remove(regio);
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

    private Regio addRegioToDatabase(Regio regio) {
        HashMap databaseData = new HashMap();

        databaseData.put("naam", regio.getName());

        int id = databaseInstance.insertInto("regio", databaseData);
        regio.setId(id);
        return regio;
    }

    private void updateRegioFromDatabase(Regio regio) {
        HashMap databaseData = new HashMap();

        databaseData.put("id", regio.getId());
        databaseData.put("naam", regio.getName());

        databaseInstance.update("regio", regio.getId(), databaseData);
    }

    private void removeRegioFromDatabase(Regio regio) {
        databaseInstance.delete("regio", regio.getId());
    }
}
