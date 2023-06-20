package services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import utils.Config;
import utils.Validator;

public class Database {
    private static Database instance;

    private static Connection connection;

    private Database() {

    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");

                String host = Config.getConfigDatabase().getProperty("DB_HOST");
                String port = Config.getConfigDatabase().getProperty("DB_PORT");
                String dbname = Config.getConfigDatabase().getProperty("DB_NAME");

                String url = "jdbc:oracle:thin:@"+ host +":"+ port +"/"+ dbname;

                String user = Config.getConfigDatabase().getProperty("DB_USER");
                String password = Config.getConfigDatabase().getProperty("DB_PASSWORD");

                DriverManager.setLoginTimeout(10);

                connection = DriverManager.getConnection(url, user, password);

                System.out.println("Connected to Oracle Database successfully");
            } catch (ClassNotFoundException | SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
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
