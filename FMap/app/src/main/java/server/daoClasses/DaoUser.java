package server.daoClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.modelClasses.ModelUsers;

public class DaoUser {

    Connection conn;

    public DaoUser(Connection conn){ this.conn = conn; }

    public void create() throws SQLException {
        String sql = "create table if not exists users (" +
                " userName text not null primary key," +
                " password text not null," +
                " email text not null," +
                " firstName text not null," +
                " lastName text not null," +
                " gender text not null," +
                " personID text not null)";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException();
        }
    }

    public void clear() throws SQLException {
        String sql = "delete from users";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }
    public void drop() throws SQLException {
        String sql = "drop table if exists users";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public void insert(ModelUsers person) throws SQLException{
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
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, person.getUserName());
            stmt.setString(2, person.getPassword());
            stmt.setString(3, person.getEmail());
            stmt.setString(4, person.getFirstName());
            stmt.setString(5, person.getLastName());
            stmt.setString(6, person.getGender());
            stmt.setString(7, person.getPersonID());
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }

    }
    public ModelUsers getUserByUsername(String userName) throws SQLException{
        ModelUsers newUser = null;
        String sql = "select * from users where userName = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            ResultSet results = stmt.executeQuery();

            if (results.next()) {
                newUser = new ModelUsers(
                        results.getString("userName"),
                        results.getString("password"),
                        results.getString("email"),
                        results.getString("firstName"),
                        results.getString("lastName"),
                        results.getString("gender"),
                        results.getString("personID")
                );
            }
            results.close();
            stmt.close();
            return newUser;
        } catch (SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
    }
}