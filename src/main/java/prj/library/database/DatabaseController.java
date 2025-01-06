package prj.library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static prj.library.utils.CLIUtils.*;
import static prj.library.utils.ConfigLoader.getProperty;

/**
 * This class is responsible for handling the connection to the database
 */
public class DatabaseController {
    private static String DB_URL; //database url to connect to
    private static String DB_USER; //database user
    private static String DB_PASSWORD; //database password
    private static Connection connection; //connection instance (to DB)

    /**
     * Configures the database connection using information from the properties file
     */
    private static void configureDB(){
        DB_URL = getProperty("database.url");
        DB_USER = getProperty("database.user");
        DB_PASSWORD = getProperty("database.password");
    }

    /**
     * Establishes a connection to the database
     * @return the connection to the database
     */
    public static Connection getConnection() {
        try {
            configureDB();
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            serverCriticalError("Database connection error --> " + e.getMessage());
        }
        return connection;
    }

    /**
     * Closes the connection to the database
     * @param connection the connection to close
     */
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            serverCriticalError("Database connection error --> " + e.getMessage());
        }
    }

}
