
package main.java.dataAccessClasses;

import main.java.modelClasses.ModelUsers;

import java.sql.*;


public class DaoUser {

    public void create(Connection conn){
        String sql = "create table if not exists users (" +
                " userName text not null primary key," +
                " password text not null," +
                " email text not null," +
                " firstName text not null," +
                " lastName text not null," +
                " gender text not null," +
                " personID text not null)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }


    public void drop(Connection conn){
        String sql = "drop table if exists users";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public void insert(ModelUsers person, Connection conn) throws SQLException{
        String sql = "insert into users ("+
                " userName," +
                " password," +
                " email," +
                " firstName," +
                " lastName," +
                " gender," +
                " personID" +
                ")" +
                " values (?,?,?,?,?,?,?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, person.getUserName());
        stmt.setString(2, person.getPassword());
        stmt.setString(3, person.getEmail());
        stmt.setString(4, person.getFirstName());
        stmt.setString(5, person.getLastName());
        stmt.setString(6, person.getGender());
        stmt.setString(7, person.getPersonID());
        stmt.executeUpdate();
        stmt.close();

    }
    public ModelUsers getUserByUsername(String userName, Connection conn) throws SQLException{
        ModelUsers newUser;
        String sql = "select * from users where userName = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, userName);
        ResultSet results = stmt.executeQuery();

        if(results.next()){
            newUser = new ModelUsers(
                    results.getString("userName"),
                    results.getString("password"),
                    results.getString("email"),
                    results.getString("firstName"),
                    results.getString("lastName"),
                    results.getString("gender"),
                    results.getString("personID")
            );
            results.close();
            stmt.close();
            return newUser;
        }else{
            throw new SQLException();
        }
    }
}