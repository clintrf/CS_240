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

    private Connection conn;
    private DaoAuthToken tokenDao ;
    private DaoPerson personDao ;
    private DaoUser userDao;
    private DaoEvent eventsDao ;

    static {
        // Name of class that implements database driver
        final String driver = "org.sqlite.JDBC";
        try{
            // Dynamically load the driver class
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public DatabaseDatabase() throws DatabaseException {
        conn = this.openConnection();

        tokenDao = new DaoAuthToken(conn);
        personDao = new DaoPerson(conn);
        userDao = new DaoUser(conn);
        eventsDao = new DaoEvent(conn);

        tokenDao.create();
        personDao.create();
        userDao.create();
        eventsDao.create();

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

        //Whenever we want to make a change to our database we will have to open a connection and use
    //Statements created by that connection to initiate transactions
    public Connection openConnection() throws DatabaseException {
        try {
            //The Structure for this Connection is driver:language:path
            //The path assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:database.db";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Unable to open connection to database");
        }

        return conn;
    }

    public Connection getConnection() throws DatabaseException {
        if(conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }

    //When we are done manipulating the database it is important to close the connection. This will
    //End the transaction and allow us to either commit our changes to the database or rollback any
    //changes that were made before we encountered a potential error.

    //IMPORTANT: IF YOU FAIL TO CLOSE A CONNECTION AND TRY TO REOPEN THE DATABASE THIS WILL CAUSE THE
    //DATABASE TO LOCK. YOUR CODE MUST ALWAYS INCLUDE A CLOSURE OF THE DATABASE NO MATTER WHAT ERRORS
    //OR PROBLEMS YOU ENCOUNTER
    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                //This will commit the changes to the database
                conn.commit();
            } else {
                //If we find out something went wrong, pass a false into closeConnection and this
                //will rollback any changes we made during this connection
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Unable to close database connection");
        }
    }

    public void clearTables() throws DatabaseException
    {
        try (Statement stmt = conn.createStatement()){
            String sql = "delete from auth_tokens\n" +
                    "delete from events\n" +
                    "delete from people\n" +
                    "delete from users";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DatabaseException("SQL Error encountered while clearing tables");
        }
    }
}

