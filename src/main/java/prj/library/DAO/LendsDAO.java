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
            String query = "INSERT INTO lends (book_id, customer_id, return_date, returned) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, lend.getBookId());
            preparedStatement.setInt(2, lend.getCustomerId());
            preparedStatement.setDate(3, Date.valueOf(lend.getReturnDate()));
            preparedStatement.setBoolean(4, lend.isReturned());

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
            String query = "UPDATE lends SET book_id = ?, customer_id = ?, return_date = ?, returned = ? WHERE lend_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, lend.getBookId());
            preparedStatement.setInt(2, lend.getCustomerId());
            preparedStatement.setDate(3, Date.valueOf(lend.getReturnDate()));
            preparedStatement.setBoolean(4, lend.isReturned());
            preparedStatement.setInt(5, lend.getId());

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
                lend = new Lends(resultSet.getInt("book_id"), resultSet.getInt("customer_id"), resultSet.getDate("return_date").toLocalDate(), resultSet.getBoolean("returned"));
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
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsReturned(Boolean returned) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE returned = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, returned);
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByCustomerId(int customerId) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE customer_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByBookId(int bookId) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE book_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLateLends() {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE return_date < ? AND returned = false";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            lends = getLendsByStatement(preparedStatement);
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
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByAllReturned(int bookId, int customerId, LocalDate returnDate, boolean returned) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE book_id = ? AND customer_id = ? AND return_date = ? AND returned = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.setDate(3, Date.valueOf(returnDate));
            preparedStatement.setBoolean(4, returned);
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByAll(int bookId, int customerId, LocalDate returnDate) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE book_id = ? AND customer_id = ? AND return_date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.setDate(3, Date.valueOf(returnDate));
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByBookIdCustomerIdReturned(int bookId, int customerId, boolean returned) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE book_id = ? AND customer_id = ? AND returned = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.setBoolean(3, returned);
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByBookIdReturnDateReturned(int bookId, LocalDate returnDate, boolean returned) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE book_id = ? AND return_date = ? AND returned = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setDate(2, Date.valueOf(returnDate));
            preparedStatement.setBoolean(3, returned);
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByCustomerIdReturnDateReturned(int customerId, LocalDate returnDate, boolean returned) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE customer_id = ? AND return_date = ? AND returned = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setDate(2, Date.valueOf(returnDate));
            preparedStatement.setBoolean(3, returned);
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByBookIdCustomerId(int bookId, int customerId) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE book_id = ? AND customer_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, customerId);
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByBookIdReturnDate(int bookId, LocalDate returnDate) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE book_id = ? AND return_date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setDate(2, Date.valueOf(returnDate));
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByBookIdReturned(int bookId, boolean returned) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE book_id = ? AND returned = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setBoolean(2, returned);
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByCustomerIdReturnDate(int customerId, LocalDate returnDate) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE customer_id = ? AND return_date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setDate(2, Date.valueOf(returnDate));
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByCustomerIdReturned(int customerId, boolean returned) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE customer_id = ? AND returned = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setBoolean(2, returned);
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    public synchronized List<Lends> getLendsByReturnDateReturned(LocalDate returnDate, boolean returned) {
        List<Lends> lends = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM lends WHERE return_date = ? AND returned = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(returnDate));
            preparedStatement.setBoolean(2, returned);
            lends = getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        return lends;
    }

    /**
     * Get lends by statement
     * @param preparedStatement the prepared statement
     * @return a list of all lends that match the given parameters
     * @throws SQLException if a database error occurs
     */
    private List<Lends> getLendsByStatement(PreparedStatement preparedStatement) throws SQLException {
        List<Lends> lends = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Lends lend = new Lends(resultSet.getInt("book_id"), resultSet.getInt("customer_id"), resultSet.getDate("return_date").toLocalDate(), resultSet.getBoolean("returned"));
            lend.setId(resultSet.getInt("lend_id"));
            lends.add(lend);
        }
        return lends;
    }
}
