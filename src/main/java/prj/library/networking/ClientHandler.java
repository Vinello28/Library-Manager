package prj.library.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import prj.library.models.Book;
import prj.library.models.Genre;
import prj.library.networking.messages.*;


public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/biblioteca"; //mysql url;
    private static final String DB_USER = "root"; //
    private static final String DB_PASSWORD = "Aridaje68"; //password

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())
        ) {
            while (true) {
                Message message = (Message) in.readObject();
                Book t = null;

                System.out.println("SERVER | DEBUG INFO: received message " + message.getOperation()+" "+message.getMessage());

                switch (message.getOperation()) {
                    case ADD_BOOK:
                        createBook((Book) message.getMessage());
                        out.writeObject(new GenericMessage(true));
                        break;
                    case GET_BOOK:
                        Book tmp = readBook((int) message.getMessage());
                        out.writeObject(new BookMessage(tmp));
                        break;
                    case UPDATE_BOOK:
                        updateBook((Book) message.getMessage());
                        out.writeObject(new GenericMessage(true));
                        break;
                    case REMOVE_BOOK:
                        deleteBook((int) message.getMessage());
                        out.writeObject(new GenericMessage(true));
                        break;
                    case GET_BOOKS:
                        List<Book> books = getAllBooks();
                        ArrayList<Book> booksArray = new ArrayList<>(books);
                        out.writeObject(new RefreshMessage(booksArray));
                        break;
                    case SEARCH_BY_TITLE:
                        t = (Book) message.getMessage();
                        List<Book> booksByTitle = getBooksByTitle(t.getTitle());
                        ArrayList<Book> booksByTitleArray = new ArrayList<>(booksByTitle);
                        out.writeObject(new RefreshMessage(booksByTitleArray));
                        break;
                    case SEARCH_BY_GENRE:
                        t = (Book) message.getMessage();
                        List<Book> booksByGenre = getBooksByGenre(t.getGenre());
                        ArrayList<Book> booksByGenreArray = new ArrayList<>(booksByGenre);
                        out.writeObject(new RefreshMessage(booksByGenreArray));
                        break;
                    case SEARCH_BY_AUTHOR: ;
                        t = (Book) message.getMessage();
                        List<Book> booksByAuthor = getBooksByAuthor(t.getAuthor());
                        ArrayList<Book> booksByAuthorArray = new ArrayList<>(booksByAuthor);
                        out.writeObject(new RefreshMessage(booksByAuthorArray));
                        break;
                    case SEARCH_BY_YEAR:
                        t = (Book) message.getMessage();
                        List<Book> booksByYear = getBooksByYear(t.getYear());
                        ArrayList<Book> booksByYearArray = new ArrayList<>(booksByYear);
                        out.writeObject(new RefreshMessage(booksByYearArray));
                        break;
                    case SEARCH_BY_ALL:
                        t = (Book) message.getMessage();
                        List<Book> booksByAll = getBooksByAllParam(t.getTitle(), t.getAuthor(), t.getGenre(), t.getYear());
                        ArrayList<Book> booksByAllArray = new ArrayList<>(booksByAll);
                        out.writeObject(new RefreshMessage(booksByAllArray));
                        break;
                    case SEARCH_BY_TITLE_AUTHOR:
                        t = (Book) message.getMessage();
                        List<Book> booksByTitleAuthor = getBooksByTitleAuthor(t.getTitle(), t.getAuthor());
                        ArrayList<Book> booksByTitleAuthorArray = new ArrayList<>(booksByTitleAuthor);
                        out.writeObject(new RefreshMessage(booksByTitleAuthorArray));
                        break;
                    case SEARCH_BY_TITLE_GENRE:
                        t = (Book) message.getMessage();
                        List<Book> booksByTitleGenre = getBooksByTitleGenre(t.getTitle(), t.getGenre());
                        ArrayList<Book> booksByTitleGenreArray = new ArrayList<>(booksByTitleGenre);
                        out.writeObject(new RefreshMessage(booksByTitleGenreArray));
                        break;
                    case SEARCH_BY_TITLE_YEAR:
                        t = (Book) message.getMessage();
                        List<Book> booksByTitleYear = getBooksByTitleYear(t.getTitle(), t.getYear());
                        ArrayList<Book> booksByTitleYearArray = new ArrayList<>(booksByTitleYear);
                        out.writeObject(new RefreshMessage(booksByTitleYearArray));
                        break;
                    case SEARCH_BY_AUTHOR_GENRE:
                        t = (Book) message.getMessage();
                        List<Book> booksByAuthorGenre = getBooksByAuthorGenre(t.getAuthor(), t.getGenre());
                        ArrayList<Book> booksByAuthorGenreArray = new ArrayList<>(booksByAuthorGenre);
                        out.writeObject(new RefreshMessage(booksByAuthorGenreArray));
                        break;
                    case SEARCH_BY_AUTHOR_YEAR:
                        t = (Book) message.getMessage();
                        List<Book> booksByAuthorYear = getBooksByAuthorYear(t.getAuthor(), t.getYear());
                        ArrayList<Book> booksByAuthorYearArray = new ArrayList<>(booksByAuthorYear);
                        out.writeObject(new RefreshMessage(booksByAuthorYearArray));
                        break;
                    case SEARCH_BY_GENRE_YEAR:
                        t = (Book) message.getMessage();
                        List<Book> booksByGenreYear = getBooksByGenreYear(t.getGenre(), t.getYear());
                        ArrayList<Book> booksByGenreYearArray = new ArrayList<>(booksByGenreYear);
                        out.writeObject(new RefreshMessage(booksByGenreYearArray));
                        break;
                    case SEARCH_BY_TITLE_AUTHOR_GENRE:
                        t = (Book) message.getMessage();
                        List<Book> booksByTitleAuthorGenre = getBooksByTitleAuthorGenre(t.getTitle(), t.getAuthor(), t.getGenre());
                        ArrayList<Book> booksByTitleAuthorGenreArray = new ArrayList<>(booksByTitleAuthorGenre);
                        out.writeObject(new RefreshMessage(booksByTitleAuthorGenreArray));
                        break;
                    case SEARCH_BY_TITLE_AUTHOR_YEAR:
                        t = (Book) message.getMessage();
                        List<Book> booksByTitleAuthorYear = getBooksByTitleAuthorYear(t.getTitle(), t.getAuthor(), t.getYear());
                        ArrayList<Book> booksByTitleAuthorYearArray = new ArrayList<>(booksByTitleAuthorYear);
                        out.writeObject(new RefreshMessage(booksByTitleAuthorYearArray));
                        break;
                    case SEARCH_BY_TITLE_GENRE_YEAR:
                        t = (Book) message.getMessage();
                        List<Book> booksByTitleGenreYear = getBooksByTitleGenreYear(t.getTitle(), t.getGenre(), t.getYear());
                        ArrayList<Book> booksByTitleGenreYearArray = new ArrayList<>(booksByTitleGenreYear);
                        out.writeObject(new RefreshMessage(booksByTitleGenreYearArray));
                        break;
                    case SEARCH_BY_AUTHOR_GENRE_YEAR:
                        t = (Book) message.getMessage();
                        List<Book> booksByAuthorGenreYear = getBooksByAuthorGenreYear(t.getAuthor(), t.getGenre(), t.getYear());
                        ArrayList<Book> booksByAuthorGenreYearArray = new ArrayList<>(booksByAuthorGenreYear);
                        out.writeObject(new RefreshMessage(booksByAuthorGenreYearArray));
                        break;
                    default:
                        out.writeObject("Invalid operation");
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client handler exception: " + e.getMessage());
        }
    }

    /**
     * Get books by all parameters
     * @param title the title of the book
     * @param author the author of the book
     * @param genre the genre of the book
     * @param year the year of the book
     * @return a list of books with the given parameters
     */
    private List<Book> getBooksByAllParam(String title, String author, Genre genre, int year) {
        check();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE title = ? AND author = ? AND genre = ? AND year = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, title);
                pstmt.setString(2, author);
                pstmt.setString(3, genre.toString());
                pstmt.setInt(4, year);
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Create a book in the database
     *
     * @param book the book to create
     */
    private void createBook(Book book) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO books (title, author, year, genre) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, book.getTitle());
                pstmt.setString(2, book.getAuthor());
                pstmt.setInt(3, book.getYear());
                if (book.getGenre() != null) pstmt.setInt(4, book.getGenre().ordinal());
                else pstmt.setNull(4, Genre.Genre.ordinal());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Read a book from the database
     *
     * @param id the id of the book to read
     * @return the book with the given id
     */
    private Book readBook(int id) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Book book = new Book();
                        book.setId(rs.getInt("id"));
                        book.setTitle(rs.getString("title"));
                        book.setAuthor(rs.getString("author"));
                        book.setYear(rs.getInt("year"));
                        if (rs.getString("genre") != null) book.setGenre(Genre.valueOf(rs.getString("genre")));
                        else book.setGenre(Genre.Genre);
                        return book;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Sends a request to the server to get all books.
     *
     * @return a list of all books
     */
    private List<Book> getAllBooks() {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books by title
     * @param title the title of the book
     * @return a list of books with the given title
     */
    private List<Book> getBooksByTitle(String title) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE title = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, title);
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books by genre
     * @param genre the genre of the book
     * @return a list of books with the given genre
     */
    private List<Book> getBooksByGenre(Genre genre) {

        check();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE genre = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, genre.toString());
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books by author
     * @param author the author of the book
     * @return a list of books with the given author
     */
    private List<Book> getBooksByAuthor(String author) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE author = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, author);
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books by year
     * @param year the year of the book
     * @return a list of books with the given year
     */
    private List<Book> getBooksByYear(int year) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE year = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, year);
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books by title and author
     * @param title the title of the book
     * @param author the author of the book
     * @return a list of books with the given title and author
     */
    private List<Book> getBooksByTitleAuthor(String title, String author) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE title = ? AND author = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, title);
                pstmt.setString(2, author);
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books by title and genre
     * @param title the title of the book
     * @param genre the genre of the book
     * @return a list of books with the given title and genre
     */
    private List<Book> getBooksByTitleGenre(String title, Genre genre) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE title = ? AND genre = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, title);
                pstmt.setString(2, genre.toString());
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books by title and year
     * @param title the title of the book
     * @param year the year of the book
     * @return a list of books with the given title and year
     */
    private List<Book> getBooksByTitleYear(String title, int year) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE title = ? AND year = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, title);
                pstmt.setInt(2, year);
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books by author and genre
     * @param author the author of the book
     * @param genre the genre of the book
     * @return a list of books with the given author and genre
     */
    private List<Book> getBooksByAuthorGenre(String author, Genre genre) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE author = ? AND genre = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, author);
                pstmt.setString(2, genre.toString());
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books by author and year
     * @param author the author of the book
     * @param year the year of the book
     * @return a list of books with the given author and year
     */
    private List<Book> getBooksByAuthorYear(String author, int year) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE author = ? AND year = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, author);
                pstmt.setInt(2, year);
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books by genre and year
     * @param genre the genre of the book
     * @param year the year of the book
     * @return a list of books with the given genre and year
     */
    private List<Book> getBooksByGenreYear(Genre genre, int year) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE genre = ? AND year = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, genre.toString());
                pstmt.setInt(2, year);
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books by title, author and genre
     * @param title the title of the book
     * @param author the author of the book
     * @param genre the genre of the book
     * @return a list of books with the given title, author and genre
     */
    private List<Book> getBooksByTitleAuthorGenre(String title, String author, Genre genre) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE title = ? AND author = ? AND genre = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, title);
                pstmt.setString(2, author);
                pstmt.setString(3, genre.toString());
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books by title, author and year
     * @param title the title of the book
     * @param author the author of the book
     * @param year the year of the book
     * @return a list of books with the given title, author and year
     */
    private List<Book> getBooksByTitleAuthorYear(String title, String author, int year) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE title = ? AND author = ? AND year = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, title);
                pstmt.setString(2, author);
                pstmt.setInt(3, year);
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books by title, genre and year
     * @param title the title of the book
     * @param genre the genre of the book
     * @param year the year of the book
     * @return a list of books with the given title, genre and year
     */
    private List<Book> getBooksByTitleGenreYear(String title, Genre genre, int year) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE title = ? AND genre = ? AND year = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, title);
                pstmt.setString(2, genre.toString());
                pstmt.setInt(3, year);
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books by author, genre and year
     * @param author the author of the book
     * @param genre the genre of the book
     * @param year the year of the book
     * @return a list of books with the given author, genre and year
     */
    private List<Book> getBooksByAuthorGenreYear(String author, Genre genre, int year) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books WHERE author = ? AND genre = ? AND year = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, author);
                pstmt.setString(2, genre.toString());
                pstmt.setInt(3, year);
                return getBooks(pstmt);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Get books chosen by the prepared statement from the database
     * @param pstmt the prepared statement
     * @return a list of books
     * @throws SQLException if the query fails
     */
    private List<Book> getBooks(PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.executeQuery()) {
            ObservableList<Book> books = FXCollections.observableArrayList();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setYear(rs.getInt("year"));
                book.setGenre(rs.getInt("genre") != 0 ? Genre.values()[rs.getInt("genre")] : Genre.Genre);
                books.add(book);
            }
            return books;
        }
    }


    /**
     * Update a book in the database
     *
     * @param book the book to update
     */
    private void updateBook(Book book) {

        check();

        System.out.println("SERVER | DEBUG INFO: updating book " + book);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE books SET title = ?, author = ?, year = ?, genre = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, book.getTitle());
                pstmt.setString(2, book.getAuthor());
                pstmt.setInt(3, book.getYear());
                pstmt.setInt(4, book.getGenre().ordinal());
                pstmt.setInt(5, book.getId());

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }


    /**
     * Delete a book from the database
     *
     * @param id the book to delete
     */
    private void deleteBook(int id) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM books WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }


    /**
     * Check if the driver is available
     */
    private void check(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        }
    }
}

