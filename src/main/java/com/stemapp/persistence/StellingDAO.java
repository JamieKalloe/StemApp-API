package com.stemapp.persistence;

import com.stemapp.database.Database;
import com.stemapp.model.Stelling;
import com.stemapp.model.Vragenlijst;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jamie on 4-4-2016.
 */
public class StellingDAO implements DAO<Stelling> {

    //Variables
    private List<Stelling> stellingen;
    private final Database databaseInstance;

    public StellingDAO() {
        this.databaseInstance = Database.getInstance();
        this.stellingen = this.getAllFromDatabase();
    }

    @Override
    public List<Stelling> getAll() {
        return this.stellingen;
    }

    public List<Stelling> getAll(int id) {
        List<Stelling> stellingList = new ArrayList<>();

        for(Stelling stelling : stellingen) {
            if(stelling.getVragenlijstId() == id) {
                stellingList.add(stelling);
            }
        }

        return stellingList;
    }

    @Override
    public Stelling get(int id) {
        try {
            for(Stelling stelling : stellingen) {
                if(stelling.getId() == id) {
                    return stelling;
                }
            }
            return null;

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(Stelling stelling) {
        stelling = this.addStellingToDatabase(stelling);
        stellingen.add(stelling);
    }

    @Override
    public void update(int id, Stelling stelling) {
        Stelling oldStelling = this.get(id);
        stelling.setId(id);

        this.updateStellingFromDatabase(stelling);
        int idInList = stellingen.indexOf(oldStelling);
        stellingen.set(idInList, stelling);
    }

    @Override
    public void delete(int id) {
        Stelling stelling = this.get(id);
        this.removeStellingFromDatabase(stelling);
        stellingen.remove(stelling);
    }

    private List<Stelling> getAllFromDatabase() {
        List<Stelling> stellingList = new ArrayList<>();
        ResultSet results = databaseInstance.select("stelling");

        try {
            while(results.next()) {
                Stelling stelling = new Stelling();

                stelling.setId(results.getInt("id"));
                stelling.setVragenlijstId(results.getInt("vragenlijst_id"));
                stelling.setTitel(results.getString("titel"));
                stelling.setStelling(results.getString("stelling"));
                stelling.setUitleg(results.getString("uitleg"));

                stellingList.add(stelling);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return stellingList;
    }

    private Stelling addStellingToDatabase(Stelling stelling) {
        HashMap databaseData = new HashMap();

        databaseData.put("vragenlijst_id", stelling.getVragenlijstId());
        databaseData.put("titel", stelling.getTitel());
        databaseData.put("stelling", stelling.getStelling());
        databaseData.put("uitleg", stelling.getUitleg());

        int id = databaseInstance.insertInto("stelling", databaseData);
        stelling.setId(id);

        return stelling;
    }

    private void updateStellingFromDatabase(Stelling stelling) {
        HashMap databaseData = new HashMap();

        databaseData.put("vragenlijst_id", stelling.getVragenlijstId());
        databaseData.put("titel", stelling.getTitel());
        databaseData.put("stelling", stelling.getStelling());
        databaseData.put("uitleg", stelling.getUitleg());

        databaseInstance.update("stelling", stelling.getId(), databaseData);
    }

    private void removeStellingFromDatabase(Stelling stelling) {
        databaseInstance.delete("stelling", stelling.getId());
    }
}
