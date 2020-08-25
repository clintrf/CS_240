package familymap.server.daoClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import familymap.server.modelClasses.ModelAuthTokens;

public class DaoAuthToken {
    Connection conn;

    public DaoAuthToken(Connection conn){ this.conn = conn; }

    public void create() throws SQLException {
        String sql =
                " create table if not exists authTokens (" +
                        " authToken text primary key," +
                        " userName text not null," +
                        " password text not null" +
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
        String sql = "delete from authTokens";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public void drop() throws SQLException {
        String sql = "drop table if exists authTokens";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }

    public void insert(ModelAuthTokens authToken) throws SQLException {
        String sql = "insert into authTokens ("+
                " authToken," +
                " userName," +
                " password" +
                ")" +
                " values (?,?,?);";
        try(PreparedStatement stmt = this.conn.prepareStatement(sql)){
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUserName());
            stmt.setString(3, authToken.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }



    public ModelAuthTokens getAuthTokenByToken(String authToken) throws SQLException{
        ModelAuthTokens newAuthToken = new ModelAuthTokens(
                "",
                "",
                ""
        );
        ResultSet resultSet;
        String sql = "select * from authTokens where authToken = ?";

        try(PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                newAuthToken = new ModelAuthTokens(
                        resultSet.getString("authToken"),
                        resultSet.getString("userName"),
                        resultSet.getString("password")
                );
            }
            resultSet.close();
            stmt.close();
            return newAuthToken;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }
    }
}