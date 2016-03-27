package com.stemapp.database;

import com.stemapp.ApiConfiguration;

import java.sql.*;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Jamie on 27-3-2016.
 */
public class Database {

    //Variables
    private Connection connection;
    private Statement statement;

    private static ApiConfiguration apiConfiguration;
    private static Database databaseInstance;

    public static synchronized Database getInstance() {
        if(databaseInstance == null) {
            databaseInstance = new Database(apiConfiguration);
        }
        return databaseInstance;
    }

    public static synchronized Database getInstance(ApiConfiguration configuration) {
        if(databaseInstance == null) {
            databaseInstance = new Database(configuration);
            apiConfiguration = configuration;
        }
        return databaseInstance;
    }

    private Database(ApiConfiguration configuration) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String driver = "jdbc:mysql://";
        String user = configuration.getDatabaseUser();
        String password = configuration.getDatabasePwd();
        String dbName = configuration.getDatabaseName();
        String url = driver + configuration.getDatabaseURL() + "/";

        try {
            this.connection = DriverManager.getConnection(url + dbName, user, password);
        }

        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet select(String from) {
        String query = "SELECT * FROM `" + from + "`";
        return queryDatabase(query);
    }

    public ResultSet select(String from, String where) {
        String query = "SELECT * FROM `" + from + "` WHERE " + where;
        return queryDatabase(query);
    }

    public ResultSet select(String from, int id) {
        String query = "SELECT * FROM `" + from + "` WHERE id=" + id;
        return queryDatabase(query);
    }

    public ResultSet select(String from, String foreignTable, String resultColumn, String filterColumn,String where) {
        String query = "SELECT * FROM `" + from + "` WHERE "+resultColumn+" IN ( SELECT "+filterColumn+" FROM "+foreignTable+ "` WHERE " + where +" )";
        return queryDatabase(query);
    }

    public int insertInto(String table, HashMap data) {
        String queryTable = "INSERT INTO `" + table + "` (";
        String queryValues = ") VALUES(";
        Set keySet = data.keySet();
        Object[] keyArray = keySet.toArray();
        for(int index = 0; index < keyArray.length; index++) {
            queryTable += ""+keyArray[index].toString()+"";
            if(data.get(keyArray[index])  instanceof  String ) {
                queryValues += "'"+data.get(keyArray[index]).toString()+"'";
            }
            else {
                queryValues += data.get(keyArray[index]).toString();
            }
            if(index != keyArray.length - 1) {
                queryTable += ", ";
                queryValues += ", ";
            }
        }
        int result = updateDatabase(queryTable + queryValues + ")", Statement.RETURN_GENERATED_KEYS);

        System.out.print(queryTable + queryValues + ")" + "  :::: " + result);

        return result;
    }

    public int update(String table, int id, HashMap data) {
        String query = "UPDATE `" + table + "` SET ";
        Set keySet = data.keySet();
        Object[] keyArray = keySet.toArray();
        for(int index = 0; index < keyArray.length; index++) {
            query += keyArray[index].toString()+"=";
            if(data.get(keyArray[index]) instanceof String ) {
                query += "'"+data.get(keyArray[index])+"'";
            }
            else {
                query += data.get(keyArray[index]).toString();
            }
            if(index != keyArray.length - 1) {
                query += ", ";
            }
        }
        query += " WHERE id="+id;
        System.out.println(query);
        int result = updateDatabase(query);
        return result;
    }

    public int update(String table, String where, HashMap data) {
        String query = "UPDATE `" + table + "` SET ";
        Set keySet = data.keySet();
        Object[] keyArray = keySet.toArray();
        for(int index = 0; index < keyArray.length; index++) {
            query += keyArray[index].toString()+"=";
            if(data.get(keyArray[index]) instanceof String ) {
                query += "'"+data.get(keyArray[index])+"'";
            }
            else {
                query += data.get(keyArray[index]).toString();
            }
            if(index != keyArray.length - 1) {
                query += ", ";
            }
        }
        query += " WHERE " + where;
        System.out.println(query);

        int result = updateDatabase(query);
        return result;
    }

    public int delete(String from, int id) {
        String query = "DELETE FROM `"+from+"` WHERE id="+id;
        int result = updateDatabase(query);
        return result;
    }

    public int delete(String from, String where) {
        String query = "DELETE FROM `"+from+"` WHERE "+where;
        return updateDatabase(query);
    }

    private ResultSet queryDatabase(String query) {
        try {
            statement = databaseInstance.connection.createStatement();
            return statement.executeQuery(query);
        }

        catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int updateDatabase(String updateQuery) {
        try {
            statement = databaseInstance.connection.createStatement();
            int result = statement.executeUpdate(updateQuery);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private int updateDatabase(String updateQuery, int settings) {
        try {
            statement = databaseInstance.connection.createStatement();
            int result = statement.executeUpdate(updateQuery, settings);
            try {
                ResultSet resultSet = statement.getGeneratedKeys();
                while(resultSet.next()) {
                    return resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void killDataBaseInstance() {
        databaseInstance = null;
    }
}
