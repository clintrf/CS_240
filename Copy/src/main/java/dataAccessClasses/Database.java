package main.java.dataAccessClasses;

import java.sql.*;

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
    }

    public Connection openConnection() {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:database.db";
            conn = DriverManager.getConnection(CONNECTION_URL);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection() {
        try{
            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


