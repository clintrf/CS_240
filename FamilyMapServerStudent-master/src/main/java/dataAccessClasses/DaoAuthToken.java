package dataAccessClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelClasses.*;

public class DaoAuthToken {
    public Connection conn;
    //public DaoAuthToken(Connection conn){
    //    this.conn = conn;
    //}

    public void create(Connection conn){
        String sql = "create table if not exists auth_tokens (" +
                " auth_token text primary key," +
                " user_name text not null," +
                " password text not null" +
                " );";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
            stmt.close();
            //return true;

        } catch (SQLException e) {
            e.printStackTrace();
            //return false;
        }
    }

    public void clear(Connection conn) {
        String sql = "delete from auth_tokens";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
            //stmt.close();
            //return true;
        } catch (SQLException e) {
           e.printStackTrace();
           //return false;
        }
    }

    public void drop(Connection conn) {
        String sql = "drop table if exists auth_tokens";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
            stmt.close();
            //return true;
        } catch (SQLException e) {
            e.printStackTrace();
            //return false;
        }
    }

    public void insert(ModelAuthToken authToken, Connection conn) {
        String sql = "insert into auth_tokens ("+
                " auth_token," +
                " user_name," +
                " password" +
                ")" +
                " values (?,?,?);";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUserName());
            stmt.setString(3, authToken.getPassword());

            stmt.executeUpdate();
            //stmt.close();
            //return true;
        }catch (SQLException e){
            e.printStackTrace();
            //return false;
        }
    }

    public boolean removeAuthTokenByToken(String authToken, Connection conn) {
        String sql = "delete from auth_tokens where auth_token = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,authToken);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void removeAuthTokensByTokens(ArrayList<String> authTokens, Connection conn) {
        for (String authToken : authTokens) {
            removeAuthTokenByToken(authToken, conn);
        }
    }

    public ModelAuthToken getAuthTokenByToken(String token, Connection conn) throws SQLException {
        String sql = "select * from auth_tokens where auth_token = ?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            ModelAuthToken authToken = new ModelAuthToken(
                    null,
                    null,
                    null
            );
            while(rs.next()) {
                authToken = new ModelAuthToken(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
                );
            }
            rs.close();
            stmt.close();
            return authToken;

    }

    public ArrayList<ModelAuthToken> findAuthTokensByTokens(ArrayList<String> tokens, Connection conn) throws SQLException {
        ModelAuthToken authToken;
        ArrayList<ModelAuthToken> authTokens = new ArrayList<>();
        for (String token : tokens){
            authToken = getAuthTokenByToken(token, conn);
            if(authToken!=null){
                authTokens.add(authToken);
            }
        }
        return authTokens;
    }

    public ModelAuthToken findAuthTokenByUserName(String userName, Connection conn) {
        String sql = "select * from auth_tokens where user_name = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            ModelAuthToken authToken = new ModelAuthToken(
                    rs.getString(null),
                    rs.getString(null),
                    rs.getString(null)
            );
            while (rs.next()) {
                authToken = new ModelAuthToken(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
                );
            }
            rs.close();
            stmt.close();
            return authToken;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
