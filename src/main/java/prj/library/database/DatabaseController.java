package prj.library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static prj.library.utils.CLIUtils.*;

/**
 * This class is responsible for handling the connection to the database
 */
public class DatabaseController {
    private static String DB_URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static String DB_USER = "root";
    private static String DB_PASSWORD = "Aridaje68";
    private static Connection connection;

    /**
     * Establishes a connection to the database
     * @return the connection to the database
     */
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            serverCriticalError("Database connection error --> " + e.getMessage());
        }
        return connection;
    }

    /**
     * Closes the connection to the database
     */
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            serverCriticalError("Database connection error --> " + e.getMessage());
        }
    }

}
