package dataAccessClasses;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import databaseClasses.DatabaseException;
import modelClasses.*;

public class DaoAuthToken {
    private Connection conn;
    public DaoAuthToken(Connection conn){
        this.conn = conn;
    }

    public void create() throws DatabaseException {
        String sql = "create table if not exists auth_tokens (" +
                " auth_token text primary key," +
                " user_name text not null," +
                " password text not null" +
                " );";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error encountered while creating auth_tokens table");
        }
    }

    public void clear() throws DatabaseException{
        String sql = "delete from auth_tokens";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("SQL Error encountered while clearing tables");
        }
    }

    public void drop() throws DatabaseException {
        String sql = "drop table if exists auth_tokens";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error encountered while dropping auth_tokens table");
        }
    }

    public void insert(ModelAuthToken authToken) throws DatabaseException {
        String sql = "insert into auth_tokens ("+
                " auth_token," +
                " user_name," +
                " password" +
                ")" +
                " values (?,?,?);";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUserName());
            stmt.setString(3, authToken.getPassword());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error encountered while inserting into the auth_tokens table");
        }
    }

    public void removeAuthTokenByToken(String authToken) throws DatabaseException {
        String sql = "delete from auth_tokens where auth_token = ?;";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1,authToken);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error encountered while removing authToken in the authTokens table");
        }
    }

    public void removeAuthTokensByTokens(ArrayList<String> authTokens) throws DatabaseException {
        for (String authToken : authTokens) {
            removeAuthTokenByToken(authToken);
        }
    }

    public ModelAuthToken findAuthTokenByToken(String token) throws DatabaseException{
        ModelAuthToken authToken;
        ResultSet rs = null;
        String sql = "select * from auth_tokens where auth_token = ?;";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new ModelAuthToken(
                        rs.getString("auth_token"),
                        rs.getString("user_name"),
                        rs.getString("password")
                );
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error encountered while finding authToken in auth_tokens table");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public ArrayList<ModelAuthToken> findAuthTokensByTokens(ArrayList<String> tokens) throws DatabaseException{
        ModelAuthToken authToken;
        ArrayList<ModelAuthToken> authTokens = new ArrayList<ModelAuthToken>();
        for (String token : tokens){
            authToken = findAuthTokenByToken(token);
            if(authToken!=null){
                authTokens.add(authToken);
            }
        }
        return authTokens;
    }

    public ModelAuthToken findAuthTokenByUserName(String userName) throws DatabaseException {
        ModelAuthToken name;
        ResultSet rs = null;
        String sql = "select * from auth_tokens where user_name = ?;";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                name = new ModelAuthToken(
                        rs.getString("auth_token"),
                        rs.getString("user_name"),
                        rs.getString("password")
                );
                return name;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error encountered while finding userName in auth_tokens table");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

}