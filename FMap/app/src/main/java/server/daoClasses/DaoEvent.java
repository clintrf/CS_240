package server.daoClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.modelClasses.ModelEvents;

public class DaoEvent {
    Connection conn;

    public DaoEvent(Connection conn){ this.conn = conn; }

    public void create() throws SQLException {
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
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public void clear() throws SQLException {
        String sql = "delete from events";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public void drop() throws SQLException {
        String sql = "drop table if exists events";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public void insert(ModelEvents event) throws SQLException {
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
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
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
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
    }


    public ModelEvents getEventById(String eventID) throws SQLException{
        ModelEvents newEvent = null;

        String sql = "select * from events where eventID = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                newEvent = new ModelEvents(
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
            }
            resultSet.close();
            stmt.close();
            return newEvent;
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
    }


    public ArrayList<ModelEvents> getEventsByAssociatedUsername(String associatedUsername) throws SQLException {
        ModelEvents event;
        ArrayList<ModelEvents> newEvent = new ArrayList<>();
        String sql = "select * from events where associatedUsername = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                event = new ModelEvents(
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
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public ArrayList<ModelEvents> getEventsByPersonID(String personID) throws SQLException {
        ModelEvents event;
        ArrayList<ModelEvents> newEvent = new ArrayList<>();
        String sql = "select * from events where personID = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                event = new ModelEvents(
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
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public void removeEventByAssociatedUsername(String associatedUsername) throws SQLException {
        String sql = "delete from events where associatedUsername = ? ";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, associatedUsername);
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }


}