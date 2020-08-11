package server.daoClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDatabase {

    private Connection conn;
    private DaoAuthToken tokenDao;
    private DaoPerson personDao;
    private DaoUser userDao;
    private DaoEvent eventsDao;

    static{
        final String driver = "org.sqlite.JDBC";
        try{ Class.forName(driver);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public DatabaseDatabase(){
        this.conn = this.openConnection();
        this.tokenDao = new DaoAuthToken(conn);
        this.personDao = new DaoPerson(conn);
        this.userDao = new DaoUser(conn);
        this.eventsDao = new DaoEvent(conn);

        try {
            this.tokenDao.create();
            this.eventsDao.create();
            this.personDao.create();
            this.userDao.create();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection openConnection() {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:database.db";
            this.conn = DriverManager.getConnection(CONNECTION_URL);
            this.conn.setAutoCommit(true);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public Connection getConnection(){
        if(this.conn == null){
            this.conn = openConnection();
        }
        return this.conn;
    }
}


