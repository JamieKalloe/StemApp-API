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
 * Created by Jamie on 1-4-2016.
 */
public class CategorieDAO implements DAO<Categorie> {

    //Variables
    private final Database databaseInstance;

    public CategorieDAO() {
        this.databaseInstance = Database.getInstance();
    }

    @Override
    public List<Categorie> getAll() {
        return this.getAllFromDatabase();
    }

    @Override
    public Categorie get(int id) {
        return this.getCategorie(id);
    }

    @Override
    public void add(Categorie categorie) {
        this.addCategorieToDatabase(categorie);
    }

    @Override
    public void update(int id, Categorie categorie) {
        this.updateCategorieFromDatabase(id, categorie);
    }

    @Override
    public void delete(int id) {
        this.removeCategorieFromDatabase(get(id));
    }

    private List<Categorie> getAllFromDatabase() {
        List<Categorie> categorieList = new ArrayList<>();
        ResultSet results = databaseInstance.select("categorie");

        try {
            while(results.next()) {
                Categorie categorie = new Categorie();

                categorie.setId(results.getInt("id"));
                categorie.setName(results.getString("naam"));

                categorieList.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return categorieList;
    }

    public Categorie getCategorie(int id) {
        ResultSet results = databaseInstance.select("categorie", id);
        Categorie categorie = new Categorie();

        try {
            if(results.next()) {
                categorie.setId(results.getInt("id"));
                categorie.setName(results.getString("naam"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return categorie;
    }

    private Categorie addCategorieToDatabase(Categorie categorie) {
        HashMap databaseData = new HashMap();

        databaseData.put("naam", categorie.getName());

        int id = databaseInstance.insertInto("categorie", databaseData);
        categorie.setId(id);

        return categorie;
    }

    private void updateCategorieFromDatabase(int id, Categorie categorie) {
        HashMap databaseData = new HashMap();

        databaseData.put("naam", categorie.getName());

        databaseInstance.update("categorie", id, databaseData);
    }

    private void removeCategorieFromDatabase(Categorie categorie) {
        databaseInstance.delete("categorie", categorie.getId());
    }
}
