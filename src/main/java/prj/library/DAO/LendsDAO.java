package prj.library.DAO;

import prj.library.models.Lends;
import prj.library.utils.CLIUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Lends
 */
public class LendsDAO implements LendsDAOInterface {
    private String DB_URL;
    private String DB_USER;
    private String DB_PASSWORD;

    public LendsDAO(String DB_URL, String DB_USER, String DB_PASSWORD) {
        this.DB_URL = DB_URL;
        this.DB_USER = DB_USER;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    public synchronized void createLend(Lends lend) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO lends (book_id, customer_id, return_date) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, lend.getBookId());
            preparedStatement.setInt(2, lend.getCustomerId());
            preparedStatement.setDate(3, Date.valueOf(lend.getReturnDate()));

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                lend.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
    }

    public synchronized void updateLend(Lends lend) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE lends SET book_id = ?, customer_id = ?, return_date = ? WHERE lend_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, lend.getBookId());
            preparedStatement.setInt(2, lend.getCustomerId());
            preparedStatement.setDate(3, Date.valueOf(lend.getReturnDate()));
            preparedStatement.setInt(4, lend.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
    }

    public synchronized void deleteLend(Lends lend) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM lends WHERE lend_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);  //TODO: check if the lend_id is received
            preparedStatement.setInt(1, lend.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
    }

    public synchronized Lends readLend(int id) {
        Lends lend = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE lend_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lend = new Lends(resultSet.getInt("book_id"), resultSet.getInt("customer_id"), resultSet.getDate("return_date").toLocalDate());
                lend.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lend;
    }

    public synchronized List<Lends> getLends() {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Lends lend = new Lends(resultSet.getInt("book_id"), resultSet.getInt("customer_id"), resultSet.getDate("return_date").toLocalDate());
                lend.setId(resultSet.getInt("lend_id"));
                lends.add(lend);
            }
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByCustomerId(int customerId) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE customer_id = ?";
            lends = lendsExtractor(customerId, connection, query);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByBookId(int bookId) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE book_id = ?";
            lends = lendsExtractor(bookId, connection, query);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLateLends() {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Lends lend = new Lends(resultSet.getInt("book_id"), resultSet.getInt("customer_id"), resultSet.getDate("return_date").toLocalDate());
                lend.setId(resultSet.getInt("lend_id"));
                if (lend.isLate()) lends.add(lend);
            }
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByReturnDate(LocalDate returnDate) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE return_date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(returnDate));
            ResultSet resultSet = preparedStatement.executeQuery();

            CLIUtils.serverDebug("here in get lends by return date..."); //TODO: remove this

            while (resultSet.next()) {
                Lends lend = new Lends(resultSet.getInt("book_id"), resultSet.getInt("customer_id"), resultSet.getDate("return_date").toLocalDate());
                lend.setId(resultSet.getInt("lend_id"));
                lends.add(lend);
            }
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    /**
     * Extracts lends from the database
     * @param customerId bookId
     * @param connection connection
     * @param query query
     * @return list of lends
     * @throws SQLException if there is an error with the database
     */
    private List<Lends> lendsExtractor(int customerId, Connection connection, String query) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, customerId);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Lends> lends = new ArrayList<>();

        while (resultSet.next()) {
            Lends lend = new Lends(resultSet.getInt("book_id"), resultSet.getInt("customer_id"), resultSet.getDate("return_date").toLocalDate());
            lend.setId(resultSet.getInt("lend_id"));
            lends.add(lend);
        }
        return lends;
    }
}
