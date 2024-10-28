package prj.library.networking.DAO;

import prj.library.models.Lends;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LendsDAO {
    private String DB_URL;
    private String DB_USER;
    private String DB_PASSWORD;

    public LendsDAO(String DB_URL, String DB_USER, String DB_PASSWORD) {
        this.DB_URL = DB_URL;
        this.DB_USER = DB_USER;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    /**
     * Creates a lend
     * @param lend lend
     */
    public synchronized void createLend(Lends lend) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO lends (book_id, return_date, cell_number, surname) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, lend.getBookId());
            statement.setDate(2, (java.sql.Date) lend.getReturnDate());
            statement.setString(3, lend.getCellphone());
            statement.setString(4, lend.getSurname());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Updates a lend
     * @param lend
     */
    public synchronized void updateLend(Lends lend) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE lends SET book_id = ?, return_date = ?, cell_number = ?, surname = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, lend.getBookId());
            statement.setDate(2, (java.sql.Date) lend.getReturnDate());
            statement.setString(3, lend.getCellphone());
            statement.setString(4, lend.getSurname());
            statement.setInt(5, lend.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Deletes a lend
     * @param id lend id
     */
    public synchronized void deleteLend(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM lends WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Reads a lend
     * @param id lend id
     * @return lend
     */
    public synchronized Lends readLend(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM lends WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            Lends res = new Lends(result.getInt(2), result.getDate(3), result.getString(4), result.getString(5));
            res.setId(result.getInt(1));
            return res;
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Reads all lends
     * @return list of lends
     */
    public synchronized List<Lends> readAllLends() {
        List<Lends> lends = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM lends";
            PreparedStatement statement = conn.prepareStatement(sql);
            resultListFIller(lends, statement);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return lends;
    }

    /**
     * Reads all lends by book id
     * @param bookId book id
     * @return list of lends
     */
    public synchronized List<Lends> readLendsByBookId(int bookId) {
        List<Lends> lends = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM lends WHERE book_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, bookId);
            resultListFIller(lends, statement);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return lends;
    }

    /**
     * Reads all lends by return date
     * @param returnDate return date
     * @return list of lends
     */
    public synchronized List<Lends> readLendsByReturnDate(Date returnDate) {
        List<Lends> lends = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM lends WHERE return_date = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1, returnDate);
            resultListFIller(lends, statement);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return lends;
    }

  /**
     * Reads all lends by cell number
     * @param cellNumber cell number
     * @return list of lends
     */
    public synchronized List<Lends> readLendsByCellNumber(String cellNumber) {
        List<Lends> lends = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM lends WHERE cell_number = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, cellNumber);
            resultListFIller(lends, statement);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return lends;
    }

    /**
     * Reads all lends by surname
     * @param surname surname
     * @return list of lends
     */
    public synchronized List<Lends> readLendsBySurname(String surname) {
        List<Lends> lends = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM lends WHERE surname = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, surname);
            resultListFIller(lends, statement);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return lends;
    }

    /**
     * Fills the list of lends
     * @param lends list of lends
     * @param statement prepared statement
     * @throws SQLException exception thrown when an error occurs in the database
     */
    private void resultListFIller(List<Lends> lends, PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            Lends tmp = new Lends(result.getInt(2), result.getDate(3), result.getString(4), result.getString(5));
            tmp.setId(result.getInt(1));
            lends.add(tmp);
        }
    }


}
