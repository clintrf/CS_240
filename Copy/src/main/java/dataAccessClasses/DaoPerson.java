package main.java.dataAccessClasses;
import main.java.modelClasses.ModelPersons;
import java.util.*;
import java.sql.*;

public class DaoPerson {
    public void create(Connection conn){
        String sql = "create table if not exists people (" +
                " personID text not null primary key," +
                " associatedUsername text not null," +
                " firstName text not null," +
                " lastName text not null," +
                " gender text not null," +
                " fatherID text," +
                " motherID text," +
                " spouseID text)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public void drop(Connection conn){
        String sql = "drop table if exists people";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public void insert(ModelPersons person, Connection conn) throws SQLException{
        String sql = "insert into people (" +
                " personID," +
                " associatedUsername," +
                " firstName," +
                " lastName," +
                " gender," +
                " fatherID," +
                " motherID," +
                " spouseID" +
                ")" +
                " values (?,?,?,?,?,?,?,?);";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, person.getPersonID());
        stmt.setString(2, person.getAssociatedUsername());
        stmt.setString(3, person.getFirstName());
        stmt.setString(4, person.getLastName());
        stmt.setString(5, person.getGender());
        stmt.setString(6, person.getFatherID());
        stmt.setString(7, person.getMotherID());
        stmt.setString(8, person.getSpouseID());
        stmt.executeUpdate();

    }
    public ModelPersons getPersonByID(String person, Connection conn) throws SQLException{
        ModelPersons newPerson;
            String sql = "select * from people where personID = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, person);
        ResultSet resultSet = stmt.executeQuery();

        if(resultSet.next()){
            newPerson = new ModelPersons(
                    resultSet.getString("personID"),
                    resultSet.getString("associatedUsername"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("gender"),
                    resultSet.getString("fatherID"),
                    resultSet.getString("motherID"),
                    resultSet.getString("spouseID")
            );
            resultSet.close();
            stmt.close();
            return newPerson;
        }else{
            throw new SQLException();
        }

    }

    public ArrayList<ModelPersons> getPeopleByAssociatedUsername(String associatedUsername, Connection conn) throws SQLException{
        ModelPersons newPerson;
        ArrayList<ModelPersons> newPeople = new ArrayList<>();
        String sql = "select * from people where associatedUsername = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, associatedUsername);
        ResultSet resultSet = stmt.executeQuery();
        while(resultSet.next()) {
            newPerson = new ModelPersons(
                    resultSet.getString("personID"),
                    resultSet.getString("associatedUsername"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("gender"),
                    resultSet.getString("fatherID"),
                    resultSet.getString("motherID"),
                    resultSet.getString("spouseID")
            );
            newPeople.add(newPerson);
        }
        resultSet.close();
        stmt.close();
        return newPeople;
    }

    public boolean removePersonByAssociatedUsername(String associatedUsername, Connection conn){
        String sql = "delete from people where associatedUsername = ? ";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, associatedUsername);
            stmt.executeUpdate();
            stmt.close();
            return true;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}