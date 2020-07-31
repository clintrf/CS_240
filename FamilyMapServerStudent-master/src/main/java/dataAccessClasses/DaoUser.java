package dataAccessClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import databaseClasses.DatabaseException;
import modelClasses.*;

public class DaoUser {
    private Connection conn;
    public DaoUser(Connection conn){
        this.conn = conn;
    }

    public void create() throws DatabaseException {
        String sql = "create table if not exists users (" +
                " user_name text primary key," +
                " password text not null," +
                " email text not null," +
                " first_name text not null," +
                " last_name text not null," +
                " gender text not null," +
                " person_id text not null" +
                " constraint ck_gender check (gender in ('f', 'm'))" +
                " );";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error create users table");
        }
    }

    public void clear() throws DatabaseException{
        String sql = "delete from users";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error clear users table");
        }
    }

    public void drop() throws DatabaseException {
        String sql = "drop table if exists users";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error drop users table");
        }
    }

    public void insert(ModelUser user) throws DatabaseException {
        String sql = "insert into users ("+
                " user_name," +
                " password," +
                " email," +
                " first_name," +
                " last_name," +
                " gender," +
                " person_id" +
                ")" +
                " values (?,?,?,?,?,?,?);";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error insert users table");
        }
    }

    public void removeUserByUserName(String userName) throws DatabaseException {
        String sql = "delete from users where user_name = ?;";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1,userName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error removeUserByUserName users table");
        }
    }

    public void removeUsersByUserNames(ArrayList<String> users) throws DatabaseException {
        for (String user : users) {
            removeUserByUserName(user);
        }
    }

    public ModelUser findUserByUserName(String userName) throws DatabaseException{
        ModelUser user;
        ResultSet rs = null;
        String sql = "select * from users where user_name = ?;";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new ModelUser(
                        rs.getString("user_name"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getString("person_id")
                );
                return user;
            }
            else {
                user = new ModelUser(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error findUserByUserName users table");
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

    public ArrayList<ModelUser> findUsersByUserNames(ArrayList<String> userNames) throws DatabaseException{
        ModelUser name;
        ArrayList<ModelUser> names = new ArrayList<>();
        for (String userName : userNames){
            name = findUserByUserName(userName);
            if(name!=null){
                names.add(name);
            }
        }
        return names;
    }
}
