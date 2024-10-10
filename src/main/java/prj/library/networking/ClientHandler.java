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

                System.out.println("SERVER | DEBUG INFO: received message " + message.getOperation()+" "+message.getOperation());

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
                        List<Book> books = getBooks();
                        ArrayList<Book> booksArray = new ArrayList<>(books);
                        out.writeObject(new RefreshMessage(booksArray));
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
     * Create a book in the database
     *
     * @param book the book to create
     */
    private void createBook(Book book) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO books (title, author, year) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, book.getTitle());
                pstmt.setString(2, book.getAuthor());
                pstmt.setInt(3, book.getYear());
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
    private List<Book> getBooks() {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM books";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    ObservableList<Book> books = FXCollections.observableArrayList();
                    while (rs.next()) {
                        Book book = new Book();
                        book.setId(rs.getInt("id"));
                        book.setTitle(rs.getString("title"));
                        book.setAuthor(rs.getString("author"));
                        book.setYear(rs.getInt("year"));
                        books.add(book);
                    }
                    return books;
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Update a book in the database
     *
     * @param book the book to update
     */
    private void updateBook(Book book) {

        check();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE books SET title = ?, author = ?, year = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, book.getTitle());
                pstmt.setString(2, book.getAuthor());
                pstmt.setInt(3, book.getYear());
                pstmt.setInt(4, book.getId());
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
     *
     * @return Nothing
     */
    private void check(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        }
    }
}

