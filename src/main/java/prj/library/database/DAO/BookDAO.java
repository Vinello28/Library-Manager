package prj.library.database.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import prj.library.models.Book;
import prj.library.models.Genre;
import prj.library.utils.CLIUtils;

import java.sql.*;
import java.util.List;

import static prj.library.database.DatabaseController.*;

/**
 * Data Access Object for Book
 */
public class BookDAO implements BookDAOInterface {

    public BookDAO() {
    }

    public synchronized void createBook(Book book) {
        Connection conn = getConnection(); //request connection to the database
        String sql = "INSERT INTO books (title, author, year, genre, copies) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getYear());
            if (book.getGenre() != null) pstmt.setInt(4, book.getGenre().ordinal());
            else pstmt.setNull(4, Genre.NULL.ordinal());
            pstmt.setInt(5, book.getCopies());
            pstmt.executeUpdate();
        }catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public synchronized Book readBook(int id) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return bookExtractor(rs);
                }
            }
        }catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized void updateBook(Book book) {
        Connection conn = getConnection();
        String sql = "UPDATE books SET title = ?, author = ?, year = ?, genre = ?, copies = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getYear());
            pstmt.setInt(4, book.getGenre().ordinal());
            pstmt.setInt(5, book.getCopies());
            pstmt.setInt(6, book.getId());
            pstmt.executeUpdate();
        }catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public synchronized void deleteBook(int id) {
        Connection conn = getConnection();
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public synchronized List<Book> getAllBooks() {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized List<Book> getBooksByTitle(String title) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE title = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized List<Book> getBooksByGenre(Genre genre) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE genre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, genre.ordinal());
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized List<Book> getBooksByAuthor(String author) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE author = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, author);
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized List<Book> getBooksByYear(int year) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE year = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, year);
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized List<Book> getBooksByTitleAuthor(String title, String author) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE title = ? AND author = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized List<Book> getBooksByTitleGenre(String title, Genre genre) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE title = ? AND genre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, genre.ordinal());
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized List<Book> getBooksByTitleYear(String title, int year) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE title = ? AND year = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, year);
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized List<Book> getBooksByAuthorGenre(String author, Genre genre) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE author = ? AND genre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, author);
            pstmt.setInt(2, genre.ordinal());
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized List<Book> getBooksByAuthorYear(String author, int year) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE author = ? AND year = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, author);
            pstmt.setInt(2, year);
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        }
        closeConnection(conn);
        return null;
    }

    public synchronized List<Book> getBooksByGenreYear(Genre genre, int year) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE genre = ? AND year = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, genre.ordinal());
            pstmt.setInt(2, year);
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized List<Book> getBooksByTitleAuthorGenre(String title, String author, Genre genre) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE title = ? AND author = ? AND genre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, genre.ordinal());
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized List<Book> getBooksByTitleAuthorYear(String title, String author, int year) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE title = ? AND author = ? AND year = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, year);
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized List<Book> getBooksByTitleGenreYear(String title, Genre genre, int year) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE title = ? AND genre = ? AND year = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, genre.ordinal());
            pstmt.setInt(3, year);
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized List<Book> getBooksByAuthorGenreYear(String author, Genre genre, int year) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE author = ? AND genre = ? AND year = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, author);
            pstmt.setInt(2, genre.ordinal());
            pstmt.setInt(3, year);
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public synchronized List<Book> getBooksByAllParam(String title, String author, Genre genre, int year) {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE title = ? AND author = ? AND genre = ? AND year = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, genre.ordinal());
            pstmt.setInt(4, year);
            return getBooks(pstmt);
        } catch (SQLException e) {
            CLIUtils.serverCriticalError("Database error: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    /**
     * Extracts books from ResultSet
     * @param pstmt PreparedStatement
     * @return List of books
     * @throws SQLException if a database access error occurs
     */
    public List<Book> getBooks(PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.executeQuery()) {
            ObservableList<Book> books = FXCollections.observableArrayList();
            while (rs.next()) {
                books.add(bookExtractor(rs));
            }
            return books;
        }
    }

    /**
     * Extracts book from ResultSet
     * @param rs ResultSet
     * @return Book
     * @throws SQLException if a database access error occurs
     */
    private Book bookExtractor(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setYear(rs.getInt("year"));
        book.setGenre(rs.getInt("genre") != 0 ? Genre.values()[rs.getInt("genre")] : Genre.NULL);
        book.setCopies(rs.getInt("copies"));
        return book;
    }

}
