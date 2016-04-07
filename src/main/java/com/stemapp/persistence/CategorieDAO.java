package com.stemapp.persistence;

import com.stemapp.database.Database;
import com.stemapp.model.Categorie;

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
    private List<Categorie> categories;
    private final Database databaseInstance;

    public CategorieDAO() {
        this.databaseInstance = Database.getInstance();
        this.categories = this.getAllFromDatabase();
    }

    @Override
    public List<Categorie> getAll() {
        return this.categories;
    }

    @Override
    public Categorie get(int id) {
        try {
            for(Categorie categorie : categories) {
                if(categorie.getId() == id) {
                    return categorie;
                }
            }
            return null;

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(Categorie categorie) {
        categorie = this.addCategorieToDatabase(categorie);
        categories.add(categorie);
    }

    @Override
    public void update(int id, Categorie categorie) {
        Categorie oldCategorie = this.get(id);
        categorie.setId(id);

        this.updateCategorieFromDatabase(categorie);
        int idInList = categories.indexOf(oldCategorie);
        categories.set(idInList, categorie);
    }

    @Override
    public void delete(int id) {
        Categorie categorie = this.get(id);
        this.removeCategorieFromDatabase(categorie);
        categories.remove(categorie);
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

    private Categorie addCategorieToDatabase(Categorie categorie) {
        HashMap databaseData = new HashMap();

        databaseData.put("naam", categorie.getName());

        int id = databaseInstance.insertInto("categorie", databaseData);
        categorie.setId(id);

        return categorie;
    }

    private void updateCategorieFromDatabase(Categorie categorie) {
        HashMap databaseData = new HashMap();

        databaseData.put("naam", categorie.getName());

        databaseInstance.update("categorie", categorie.getId(), databaseData);
    }

    private void removeCategorieFromDatabase(Categorie categorie) {
        databaseInstance.delete("categorie", categorie.getId());
    }
}
