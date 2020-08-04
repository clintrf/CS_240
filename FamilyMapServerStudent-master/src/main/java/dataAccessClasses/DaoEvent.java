package dataAccessClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import databaseClasses.DatabaseException;
import modelClasses.*;

public class DaoEvent {

    public void create(Connection conn){
        String sql = "create table if not exists events (" +
                " eventID text primary key," +
                " associatedUsername text not null," +
                " personID text not null," +
                " latitude real not null," +
                " longitude real not null," +
                " country text not null," +
                " city text not null," +
                " eventType text not null," +
                " year int not null" +
                " );";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public void clear(Connection conn) {
        String sql = "delete from events";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void drop(Connection conn){
        String sql = "drop table if exists events";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public void insert(ModelEvent event, Connection conn) throws SQLException {
        String sql = "insert into events (" +
                " eventID," +
                " associatedUsername," +
                " personID," +
                " latitude," +
                " longitude," +
                " country," +
                " city," +
                " eventType," +
                " year" +
                ")" +
                " values (?,?,?,?,?,?,?,?,?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, event.getEventID());
        stmt.setString(2, event.getAssociatedUsername());
        stmt.setString(3, event.getPersonID());
        stmt.setDouble(4, event.getLatitude());
        stmt.setDouble(5, event.getLongitude());
        stmt.setString(6, event.getCountry());
        stmt.setString(7, event.getCity());
        stmt.setString(8, event.getEventType());
        stmt.setInt(9, event.getYear());
        stmt.executeUpdate();


    }


    public ModelEvent getEventById(String eventID, Connection conn) throws SQLException{
        ModelEvent newEvent;
        String sql = "select * from events where eventID = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                newEvent = new ModelEvent(
                        resultSet.getString("eventID"),
                        resultSet.getString("associatedUsername"),
                        resultSet.getString("personID"),
                        resultSet.getDouble("latitude"),
                        resultSet.getDouble("longitude"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getString("eventType"),
                        resultSet.getInt("year")
                );
                resultSet.close();
                stmt.close();
                return newEvent;
            }
            throw new SQLException();
        }
    }


    public ArrayList<ModelEvent> getEventsByAssociatedUsername(String associatedUsername, Connection conn) throws SQLException {
        ModelEvent event;
        ArrayList<ModelEvent> newEvent = new ArrayList<>();
        String sql = "select * from events where associatedUsername = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, associatedUsername);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            event = new ModelEvent(
                    resultSet.getString("eventID"),
                    resultSet.getString("associatedUsername"),
                    resultSet.getString("personID"),
                    resultSet.getDouble("latitude"),
                    resultSet.getDouble("longitude"),
                    resultSet.getString("country"),
                    resultSet.getString("city"),
                    resultSet.getString("eventType"),
                    resultSet.getInt("year"));
            newEvent.add(event);
        }
        resultSet.close();
        stmt.close();
        return newEvent;
    }

    public boolean removeEventByAssociatedUsername(String associatedUsername, Connection conn){
        String sql = "delete from events where associatedUsername = ? ";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, associatedUsername);
            stmt.executeUpdate();
            return true;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
