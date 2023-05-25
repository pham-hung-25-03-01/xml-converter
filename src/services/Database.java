package services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import utils.Config;

public class Database {
    private static class DatabaseHelper {
        private static final Database INSTANCE = new Database();
    }

    private Connection connection;

    private Database() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String host = Config.getConfigDatabase().getProperty("DB_HOST");
            String port = Config.getConfigDatabase().getProperty("DB_PORT");
            String dbname = Config.getConfigDatabase().getProperty("DB_NAME");

            String url = "jdbc:oracle:thin:@"+ host +":"+ port +"/"+ dbname;

            String user = Config.getConfigDatabase().getProperty("DB_USER");
            String password = Config.getConfigDatabase().getProperty("DB_PASSWORD");

            this.connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to Oracle Database");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Oracle JDBC Driver not found");
        } catch (SQLException e) {
            throw new RuntimeException("Connection Failed");
        } catch (IOException e) {
            throw new RuntimeException("Config file not found");
        } 
    }

    public static Database getInstance() {
        return DatabaseHelper.INSTANCE;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public static void main(String[] args) {
        Database db = Database.getInstance();
        System.out.println(db.getConnection());
    }
}
