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
        return this.getAllFromDatabase();
    }

    public List<Stelling> getAll(int id) {
        return this.getAllFromDatabase(id);
    }

    @Override
    public Stelling get(int id) {
        return this.getStelling(id);
    }

    @Override
    public void add(Stelling stelling) {
        stelling = this.addStellingToDatabase(stelling);
        stellingen.add(stelling);
    }

    @Override
    public void update(int id, Stelling stelling) {
        this.updateStellingFromDatabase(id, stelling);
    }

    @Override
    public void delete(int id) {
        this.removeStellingFromDatabase(get(id));
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

    private List<Stelling> getAllFromDatabase(int vragenlijstId) {
        List<Stelling> stellingList = new ArrayList<>();
        ResultSet results = databaseInstance.select("stelling", "vragenlijst_id=" + vragenlijstId);

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

    private Stelling getStelling(int id) {
        ResultSet results = databaseInstance.select("stelling", id);
        Stelling stelling = new Stelling();

        try {
            if(results.next()) {
                stelling.setId(results.getInt("id"));
                stelling.setVragenlijstId(results.getInt("vragenlijst_id"));
                stelling.setTitel(results.getString("titel"));
                stelling.setStelling(results.getString("stelling"));
                stelling.setUitleg(results.getString("uitleg"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return stelling;
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

    private void updateStellingFromDatabase(int id, Stelling stelling) {
        HashMap databaseData = new HashMap();

        databaseData.put("vragenlijst_id", stelling.getVragenlijstId());
        databaseData.put("titel", stelling.getTitel());
        databaseData.put("stelling", stelling.getStelling());
        databaseData.put("uitleg", stelling.getUitleg());

        databaseInstance.update("stelling", id, databaseData);
    }

    private void removeStellingFromDatabase(Stelling stelling) {
        databaseInstance.delete("stelling", stelling.getId());
    }
}
