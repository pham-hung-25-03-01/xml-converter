package services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import utils.Config;
import utils.Validator;

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

            DriverManager.setLoginTimeout(10);

            this.connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to Oracle Database successfully");
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

    public boolean closeConnection() {
        try {
            this.connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean testConnection(String host, String port, String dbname, String user, String password) {
        Validator validator = new Validator();

        if (!validator.isHost(host)) {
            throw new IllegalArgumentException("Host is not valid");
        }

        if (!validator.isPort(port)) {
            throw new IllegalArgumentException("Port is not valid");
        }

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url = "jdbc:oracle:thin:@"+ host +":"+ port +"/"+ dbname;

            DriverManager.setLoginTimeout(10);

            DriverManager.getConnection(url, user, password).close();

            return true;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Oracle JDBC Driver not found");
        } catch (SQLException e) {
            return false;
        }
    }

}
