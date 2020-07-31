package dataAccessClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import databaseClasses.DatabaseException;
import modelClasses.*;

public class DaoPerson {
    private Connection conn;

    public DaoPerson(Connection conn) {
        this.conn = conn;
    }

    public void create() throws DatabaseException {
        String sql = "create table if not exists people (" +
                " person_id text primary key," +
                " associated_user_name text not null," +
                " first_name text not null," +
                " last_name text not null," +
                " gender text not null," +
                " father_id text," +
                " mother_id text," +
                " spouse_id text" +
                " constraint ck_gender check (gender in ('f', 'm'))" +
                " );";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error create people table");
        }
    }

    public void clear() throws DatabaseException{
        String sql = "delete from people";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error clear people table");
        }
    }

    public void drop() throws DatabaseException {
        String sql = "drop table if exists people";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error drop people table");
        }
    }

    public void insert(ModelPerson person) throws DatabaseException {
        String sql = "insert into people (" +
                " person_id," +
                " associated_user_name," +
                " first_name," +
                " last_name," +
                " gender," +
                " father_id," +
                " mother_id," +
                " spouse_id" +
                ")" +
                " values (?,?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error insert people table");
        }
    }

    public void removePersonById(String personId) throws DatabaseException {
        String sql = "delete from people where person_id = ?;";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, personId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error removePersonById people table");
        }
    }

    public void removePeopleByIds(ArrayList<String> people) throws DatabaseException {
        for (String person : people) {
            removePersonById(person);
        }
    }

    public ModelPerson findPersonById(String personId) throws DatabaseException {
        ModelPerson person;
        ResultSet rs = null;
        String sql = "select * from people where person_id = ?;";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, personId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new ModelPerson(
                        rs.getString("person_id"),
                        rs.getString("associated_user_name"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("father_id"),
                        rs.getString("mother_id"),
                        rs.getString("spouse_id"));
                return person;
            }
            else{
                person = new ModelPerson(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error findPersonById people table");
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

    public ArrayList<ModelPerson> findPeopleByIds(ArrayList<String> personIds) throws DatabaseException{
        ModelPerson person;
        ArrayList<ModelPerson> people = new ArrayList<>();
        for (String personId : personIds){
            person = findPersonById(personId);
            if(person!=null){
                people.add(person);
            }
        }
        return people;
    }

    public ArrayList<ModelPerson> findPeopleByAssociatedUserName(String associatedUserName) throws DatabaseException {
        ModelPerson person;
        ArrayList<ModelPerson> people = new ArrayList<>();
        ResultSet rs = null;
        String sql = "select * from people where associated_user_name = ?;";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUserName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                person = new ModelPerson(rs.getString("person_id"),
                        rs.getString("associated_user_name"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("father_id"),
                        rs.getString("mother_id"),
                        rs.getString("spouse_id"));
                people.add(person);
            }
            return people;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error findPeopleByAssociatedUserName people table");
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
