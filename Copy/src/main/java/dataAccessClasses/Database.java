package main.java.dataAccessClasses;

import java.sql.*;

/**
 * Created by logan on 10/25/2017.
 */

//handles the access classes and how they manage the database
public class Database {

    static{
        try{
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    DaoAuthToken tokenAccess = new DaoAuthToken();
    DaoPerson personAccess = new DaoPerson();
    DaoUser userAccess = new DaoUser();
    DaoEvent eventsAccess = new DaoEvent();

    private Connection conn;

    public Database(){

        conn = this.openConnection();
        tokenAccess.create(conn);
        personAccess.create(conn);
        eventsAccess.create(conn);
        userAccess.create(conn);

        tokenAccess.drop(conn);
        tokenAccess.create(conn);

//        this.closeConnection();
        //create database
        //load driver
    }

    public Connection openConnection() {

        try {
            //should this be sqlite or db?
            final String CONNECTION_URL = "jdbc:sqlite:database.db";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
//            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection() {
        try{
//            if(commit){
//                conn.commit();
//            }
//            else{
//                conn.rollback();
//            }

            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    //have to define database exception

    }
}


