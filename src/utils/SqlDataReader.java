package utils;

import java.io.IOException;
import java.sql.Connection;

import services.Database;

public class SqlDataReader {
    private Connection db = Database.getInstance().getConnection();
    
    public Object getDataFromQuery(String query) {
        try {
            var stmt = db.createStatement();
            var rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}