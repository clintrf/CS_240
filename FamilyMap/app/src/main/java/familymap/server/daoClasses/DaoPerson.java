package familymap.server.daoClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import familymap.server.modelClasses.ModelPersons;

public class DaoPerson {

    Connection conn;

    public DaoPerson(Connection conn){ this.conn = conn; }

    public void create() throws SQLException {
        String sql = "create table if not exists people (" +
                " personID text not null primary key," +
                " associatedUsername text not null," +
                " firstName text not null," +
                " lastName text not null," +
                " gender text not null," +
                " fatherID text," +
                " motherID text," +
                " spouseID text)";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
    }
    public void clear() throws SQLException {
        String sql = "delete from people";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }
    public void drop() throws SQLException {
        String sql = "drop table if exists people";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public void insert(ModelPersons person) throws SQLException{
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

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }

    }
    public ModelPersons getPersonByID(String person) throws SQLException{
        ModelPersons newPerson = null;
        String sql = "select * from people where personID = ?";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, person);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
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
            }
            resultSet.close();
            stmt.close();
            return newPerson;

        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public ArrayList<ModelPersons> getPeopleByAssociatedUsername(String associatedUsername) throws SQLException{
        ModelPersons newPerson;
        ArrayList<ModelPersons> newPeople = new ArrayList<>();
        String sql = "select * from people where associatedUsername = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
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
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public void removePersonByAssociatedUsername(String associatedUsername) throws SQLException {
        String sql = "delete from people where associatedUsername = ? ";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, associatedUsername);
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }
}