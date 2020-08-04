package databaseClasses;

import dataAccessClasses.DaoAuthToken;
import dataAccessClasses.DaoEvent;
import dataAccessClasses.DaoPerson;
import dataAccessClasses.DaoUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDatabase {

    public Connection conn;
    public DaoAuthToken tokenDao ;
    public DaoPerson personDao ;
    public DaoUser userDao;
    public DaoEvent eventsDao ;

    static {
        final String driver = "org.sqlite.JDBC";
        try{ Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public DatabaseDatabase() {
        conn = this.openConnection();

        tokenDao = new DaoAuthToken();
        personDao = new DaoPerson();
        userDao = new DaoUser();
        eventsDao = new DaoEvent();



        tokenDao.create(conn);
        personDao.create(conn);
        userDao.create(conn);
        eventsDao.create(conn);

        tokenDao.drop(conn);

        tokenDao.create(conn);


    }

    public DaoAuthToken getTokenDao(){
        return  tokenDao;
    }
    public DaoPerson getPersonDao(){
        return personDao;
    }
    public DaoUser getUserDao(){
        return userDao;
    }
    public DaoEvent getEventsDao(){
        return eventsDao;
    }

    public Connection openConnection() {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:database.db";
            conn = DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public Connection getConnection()  {
        if(conn == null) {
            conn = this.openConnection();
        }
        return conn;
    }

    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                conn.commit();
            } else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Unable to close database connection");
        }
    }

    public void clearTables() throws DatabaseException {
        try (Statement stmt = conn.createStatement()){
            String sql =
                    "delete from auth_tokens\n" +
                    "delete from events\n" +
                    "delete from people\n" +
                    "delete from users";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DatabaseException("Error clearTables all tables");
        }
    }
}

