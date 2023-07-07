package common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlDataReader {
    public static String getData(String query) throws SQLException {
        Connection db = Database.getInstance().getConnection();
        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            return rs.getObject(1).toString();
        }
        return "";
    }
}