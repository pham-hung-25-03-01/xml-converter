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

    public static void main(String[] args) throws IOException {
        var reader = new SqlDataReader();
        var query = Config.getQueries().getProperty("BANKING_DATE");
        System.out.println(query);
        var result = reader.getDataFromQuery(query);
        System.out.println(result);
    }
}