package common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

                String host = Config.getDatabase("DB_HOST");
                String port = Config.getDatabase("DB_PORT");
                String dbname = Config.getDatabase("DB_NAME");
                String user = Config.getDatabase("DB_USER");
                String password = Config.getDatabase("DB_PASSWORD");

                if (host == null || port == null || dbname == null || user == null || password == null || host.isEmpty() || port.isEmpty() || dbname.isEmpty() || user.isEmpty() || password.isEmpty()) {
                    throw new RuntimeException("Database config is invalid");
                }

                String url = "jdbc:oracle:thin:@"+ host +":"+ port +"/"+ dbname;

                DriverManager.setLoginTimeout(10);

                connection = DriverManager.getConnection(url, user, password);
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