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
    public void createLend(Lends lend) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO lends (book_id, return_date) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, lend.getBookId());
            statement.setDate(2, (java.sql.Date) lend.getReturnDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a lend
     * @param lend
     */
    public void updateLend(Lends lend) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE lends SET book_id = ?, return_date = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, lend.getBookId());
            statement.setDate(2, (java.sql.Date) lend.getReturnDate());
            statement.setInt(3, lend.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a lend
     * @param id lend id
     */
    public void deleteLend(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM lends WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a lend
     * @param id lend id
     * @return lend
     */
    public Lends readLend(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM lends WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            return new Lends(result.getInt(1), result.getInt(2), result.getDate(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads all lends
     * @return list of lends
     */
    public List<Lends> readAllLends() {
        List<Lends> lends = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM lends";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                lends.add(new Lends(result.getInt(1), result.getInt(2), result.getDate(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lends;
    }

    /**
     * Reads all lends by book id
     * @param bookId book id
     * @return list of lends
     */
    public List<Lends> readLendsByBookId(int bookId) {
        List<Lends> lends = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM lends WHERE book_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, bookId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                lends.add(new Lends(result.getInt(1), result.getInt(2), result.getDate(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lends;
    }

    /**
     * Reads all lends by return date
     * @param returnDate return date
     * @return list of lends
     */
    public List<Lends> readLendsByReturnDate(Date returnDate) {
        List<Lends> lends = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM lends WHERE return_date = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1, returnDate);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                lends.add(new Lends(result.getInt(1), result.getInt(2), result.getDate(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lends;
    }


}
