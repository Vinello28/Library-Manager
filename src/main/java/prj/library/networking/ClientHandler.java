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
import prj.library.models.Book;


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
                String operation = (String) in.readObject();
                Book book = (Book) in.readObject();

                switch (operation) {
                    case "CREATE":
                        createBook(book);
                        out.writeObject("Book created successfully");
                        break;
                    case "READ":
                        Book readBook = readBook(book.getId());
                        out.writeObject(readBook);
                        break;
                    case "UPDATE":
                        updateBook(book);
                        out.writeObject("Book updated successfully");
                        break;
                    case "DELETE":
                        deleteBook(book.getId());
                        out.writeObject("Book deleted successfully");
                        break;
                    default:
                        out.writeObject("Invalid operation");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client handler exception: " + e.getMessage());
        }
    }

    private void createBook(Book book) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        }
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

    private Book readBook(int id) {
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

    private void updateBook(Book book) {
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

    private void deleteBook(int id) {
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
}

