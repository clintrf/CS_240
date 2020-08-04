package dataAccessClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import databaseClasses.DatabaseException;
import modelClasses.*;

public class DaoEvent {
    //private Connection conn;
//    public DaoEvent(Connection conn){
//        this.conn = conn;
//    }

    public void create(Connection conn)  {
        String sql = "create table if not exists events (" +
                " event_id text primary key," +
                " associated_user_name text not null," +
                " person_id text not null," +
                " latitude real not null," +
                " longitude real not null," +
                " country text not null," +
                " city text not null," +
                " event_type text not null," +
                " year integer not null" +
                " );";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            //throw new DatabaseException("Error create events table");
        }
    }

    public void clear(Connection conn) throws DatabaseException{
        String sql = "delete from events";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error clear events table");
        }
    }

    public void drop(Connection conn) throws DatabaseException {
        String sql = "drop table if exists events";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error drop events table");
        }
    }

    public void insert(ModelEvent event,Connection conn ) throws DatabaseException {
        String sql = "insert into events ("+
                " event_id," +
                " associated_user_name," +
                " person_id," +
                " latitude," +
                " longitude," +
                " country," +
                " city," +
                " event_type," +
                " year" +
                ")" +
                " values (?,?,?,?,?,?,?,?,?);";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
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
        } catch (SQLException e) {
            throw new DatabaseException("Error insert events table");
        }
    }

    public void removeEventById(String eventId, Connection conn) throws DatabaseException {
        String sql = "delete from events where event_id = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,eventId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error removeEventById events table");
        }
    }

    public void removeEventsByIds(ArrayList<String> eventIds, Connection conn) throws DatabaseException {
        for(String eventId : eventIds){
            removeEventById(eventId, conn);
        }
    }

    public ModelEvent findEventById(String eventId, Connection conn) throws DatabaseException{
        ModelEvent event;
        ResultSet rs = null;
        String sql = "select * from events where event_id = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new ModelEvent(rs.getString("event_id"),
                        rs.getString("associated_user_name"),
                        rs.getString("person_id"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("event_type"),
                        rs.getInt("year"));
                return event;
            }
            else{
                event = new ModelEvent(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error findEventById events table");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<ModelEvent> findEventsByIds(ArrayList<String> eventIds, Connection conn) throws DatabaseException{
        ModelEvent event;
        ArrayList<ModelEvent> events = new ArrayList<>();
        for (String eventId : eventIds){
            event = findEventById(eventId, conn);
            if(event!=null){
                events.add(event);
            }
        }
        return events;
    }

    public ArrayList<ModelEvent> findEventsByAssociatedUserName(String associatedUserName, Connection conn) throws DatabaseException {
        ModelEvent event;
        ArrayList<ModelEvent> events = new ArrayList<>();
        ResultSet rs = null;
        String sql = "select * from events where associated_user_name = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUserName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                event = new ModelEvent(rs.getString("event_id"),
                        rs.getString("associated_user_name"),
                        rs.getString("person_id"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("event_type"),
                        rs.getInt("year"));
                events.add(event);
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error findEventsByAssociatedUserName events table");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
