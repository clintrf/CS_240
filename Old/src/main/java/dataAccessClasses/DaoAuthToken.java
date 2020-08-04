package dataAccessClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelClasses.*;

public class DaoAuthToken {
    private Connection conn;
    public DaoAuthToken(Connection conn){
        this.conn = conn;
    }

    public boolean create() {
        String sql = "create table if not exists auth_tokens (" +
                " auth_token text primary key," +
                " user_name text not null," +
                " password text not null" +
                " );";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean clear() {
        String sql = "delete from auth_tokens";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean drop() {
        String sql = "drop table if exists auth_tokens";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(ModelAuthToken authToken) {
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
            stmt.close();
            return true;
        } catch (SQLException e) {
           return false;
        }
    }

    public boolean removeAuthTokenByToken(String authToken) {
        String sql = "delete from auth_tokens where auth_token = ?;";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1,authToken);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
           return false;
        }
    }

    public void removeAuthTokensByTokens(ArrayList<String> authTokens) {
        for (String authToken : authTokens) {
            removeAuthTokenByToken(authToken);
        }
    }

    public ModelAuthToken getAuthTokenByToken(String token){
        ModelAuthToken authToken = new ModelAuthToken(
                null,
                null,
                null
        );
        ResultSet rs = null;
        String sql = "select * from auth_tokens where auth_token = ?;";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            while (rs.next()) {
                authToken = new ModelAuthToken(
                        rs.getString("auth_token"),
                        rs.getString("user_name"),
                        rs.getString("password")
                );
            }
            return authToken;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return authToken;
    }

    public ArrayList<ModelAuthToken> getAuthTokensByTokens(ArrayList<String> tokens) {
        ModelAuthToken authToken;
        ArrayList<ModelAuthToken> authTokens = new ArrayList<>();
        for (String token : tokens){
            authToken = getAuthTokenByToken(token);
            if(authToken!=null){
                authTokens.add(authToken);
            }
        }
        return authTokens;
    }

    public ModelAuthToken getAuthTokenByUserName(String userName){
        ModelAuthToken name = new ModelAuthToken(
                null,
                null,
                null
        );
        ResultSet rs = null;
        String sql = "select * from auth_tokens where user_name = ?;";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                name = new ModelAuthToken(
                        rs.getString("auth_token"),
                        rs.getString("user_name"),
                        rs.getString("password")
                );
            }
            return name;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return name;
    }
}
