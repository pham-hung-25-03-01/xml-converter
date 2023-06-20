package utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import services.Database;

public class SqlDataReader {
    public static String getData(String dataName) throws IOException, SQLException {
        String query = Config.getQueries().getProperty(dataName);
        return getDataFromQuery(query);
    }
    
    private static String getDataFromQuery(String query) throws SQLException {
        Connection db = Database.getInstance().getConnection();
        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            return rs.getObject(1).toString();
        }
        return "";
    }
}