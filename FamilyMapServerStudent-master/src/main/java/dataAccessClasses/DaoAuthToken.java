package dataAccessClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelClasses.*;
public class DaoAuthToken {

    public void create(Connection conn){
        String sql =
                " create table if not exists authTokens (" +
                        " authToken text primary key," +
                        " userName text not null," +
                        " password text not null" +
                        " );";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void clear(Connection conn) {
        String sql = "delete from authTokens";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void drop(Connection conn){
        String sql = "drop table if exists authTokens";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(ModelAuthToken authToken, Connection conn){
        String sql = "insert into authTokens ("+
                " authToken," +
                " userName," +
                " password" +
                ")" +
                " values (?,?,?);";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUserName());
            stmt.setString(3, authToken.getPassword());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    public ModelAuthToken getAuthTokenByToken(String authToken, Connection conn) throws SQLException{
        ModelAuthToken newAuthToken = new ModelAuthToken(
                "",
                "",
                ""
        );
        ResultSet resultSet = null;
        String sql = "select * from authTokens where authToken = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, authToken);
        resultSet = stmt.executeQuery();
        while(resultSet.next()){
            newAuthToken = new ModelAuthToken(
                    resultSet.getString("authToken"),
                    resultSet.getString("userName"),
                    resultSet.getString("password")
            );
        }
        resultSet.close();
        stmt.close();
        return newAuthToken;
    }

    public ArrayList<ModelAuthToken> getAuthTokensByTokens(ArrayList<String> authTokens, Connection conn) throws SQLException {
        ModelAuthToken authToken;
        ArrayList<ModelAuthToken> newAuthTokens = new ArrayList<>();
        for (String token : authTokens){
            authToken = getAuthTokenByToken(token, conn);
            if(authToken!=null){
                newAuthTokens.add(authToken);
            }
        }
        return newAuthTokens;
    }

    public ModelAuthToken getAuthTokenByUsername(String userName, Connection conn) throws SQLException {
        ModelAuthToken newAuthToken = new ModelAuthToken(
                "",
                "",
                ""
        );
        ResultSet resultSet = null;
        String sql = "select * from authTokens where userName = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, userName);
        resultSet = stmt.executeQuery();
        while(resultSet.next()){
            newAuthToken = new ModelAuthToken(
                    resultSet.getString("authToken"),
                    resultSet.getString("userName"),
                    resultSet.getString("password")
            );
        }
        resultSet.close();
        stmt.close();
        return newAuthToken;
    }


    public boolean removeAuthTokenByToken(String authToken, Connection conn){
        String sql = "delete from auth_tokens where auth_token = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,authToken);
            stmt.executeUpdate();
            stmt.close();
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




}