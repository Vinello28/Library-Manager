package prj.library.database.DAO;

import prj.library.models.Lends;
import prj.library.notification.VirtualLend;
import prj.library.utils.CLIUtils;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static prj.library.database.DatabaseController.*;

/**
 * Data Access Object for Lends
 */
public class LendsDAO implements LendsDAOInterface {

    public LendsDAO() {
    }

    public synchronized void createLend(Lends lend) {
        Connection connection = getConnection();
        String query = "INSERT INTO lends (book_id, customer_id, return_date, returned) VALUES (?, ?, ?, ?)";
        try {
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
        } finally {
            closeConnection(connection);
        }
    }

    public synchronized void updateLend(Lends lend) {
        Connection connection = getConnection();
        String query = "UPDATE lends SET book_id = ?, customer_id = ?, return_date = ?, returned = ? WHERE lend_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, lend.getBookId());
            preparedStatement.setInt(2, lend.getCustomerId());
            preparedStatement.setDate(3, Date.valueOf(lend.getReturnDate()));
            preparedStatement.setBoolean(4, lend.isReturned());
            preparedStatement.setInt(5, lend.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    public synchronized void deleteLend(Lends lend) {
        Connection connection = getConnection();
        String query = "DELETE FROM lends WHERE lend_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, lend.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    public synchronized Lends readLend(int id) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE lend_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            Lends lend = null;
            if (resultSet.next()) {
                lend = new Lends(resultSet.getInt("book_id"), resultSet.getInt("customer_id"), resultSet.getDate("return_date").toLocalDate(), resultSet.getBoolean("returned"));
                lend.setId(resultSet.getInt("lend_id"));
            }
            return lend;
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLends() {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsReturned(Boolean returned) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE returned = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, returned);
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsByCustomerId(int customerId) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE customer_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsByBookId(int bookId) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE book_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLateLends() {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE return_date < ? AND returned = false";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsByReturnDate(LocalDate returnDate) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE return_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(returnDate));
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsByAllReturned(int bookId, int customerId, LocalDate returnDate, boolean returned) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE book_id = ? AND customer_id = ? AND return_date = ? AND returned = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.setDate(3, Date.valueOf(returnDate));
            preparedStatement.setBoolean(4, returned);
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsByAll(int bookId, int customerId, LocalDate returnDate) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE book_id = ? AND customer_id = ? AND return_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.setDate(3, Date.valueOf(returnDate));
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsByBookIdCustomerIdReturned(int bookId, int customerId, boolean returned) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE book_id = ? AND customer_id = ? AND returned = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.setBoolean(3, returned);
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsByBookIdReturnDateReturned(int bookId, LocalDate returnDate, boolean returned) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE book_id = ? AND return_date = ? AND returned = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setDate(2, Date.valueOf(returnDate));
            preparedStatement.setBoolean(3, returned);
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsByCustomerIdReturnDateReturned(int customerId, LocalDate returnDate, boolean returned) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE customer_id = ? AND return_date = ? AND returned = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setDate(2, Date.valueOf(returnDate));
            preparedStatement.setBoolean(3, returned);
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsByBookIdCustomerId(int bookId, int customerId) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE book_id = ? AND customer_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, customerId);
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsByBookIdReturnDate(int bookId, LocalDate returnDate) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE book_id = ? AND return_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setDate(2, Date.valueOf(returnDate));
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsByBookIdReturned(int bookId, boolean returned) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE book_id = ? AND returned = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setBoolean(2, returned);
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsByCustomerIdReturnDate(int customerId, LocalDate returnDate) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE customer_id = ? AND return_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setDate(2, Date.valueOf(returnDate));
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsByCustomerIdReturned(int customerId, boolean returned) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE customer_id = ? AND returned = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setBoolean(2, returned);
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized List<Lends> getLendsByReturnDateReturned(LocalDate returnDate, boolean returned) {
        Connection connection = getConnection();
        String query = "SELECT * FROM lends WHERE return_date = ? AND returned = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(returnDate));
            preparedStatement.setBoolean(2, returned);
            return getLendsByStatement(preparedStatement);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public synchronized int getNotReturnedLendsCount() {
        int count = 0;
        Connection connection = getConnection();
        String query = "SELECT COUNT(*) FROM lends WHERE returned = false";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return 0;
    }

    public synchronized List<VirtualLend> getLateLendsNotification() {
        List<VirtualLend> lends = new ArrayList<>();
        Connection connection = getConnection();
        String query = "SELECT b.title, c.email, c.name, l.return_date FROM lends l " +
                "JOIN books b ON l.book_id = b.id " +
                "JOIN customers c ON l.customer_id = c.id_c " +
                "WHERE l.return_date < ? AND l.returned = false";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                VirtualLend lend = new VirtualLend(resultSet.getString("title"), resultSet.getString("email"), resultSet.getString("name"), resultSet.getDate("return_date").toLocalDate());
                lends.add(lend);
            }
            return lends;
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(connection);
        }
        return null;
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
