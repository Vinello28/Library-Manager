package prj.library.networking;

import prj.library.models.Book;
import prj.library.models.Genre;
import prj.library.networking.messages.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 60129;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * Starts the client by connecting to the server and initializing the input and output streams.
     */
    public void start() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Sends a request to the server to create a book.
     *
     * @param book the book to create
     * @return true if the book was created, false otherwise
     */
    public Boolean createBook(Book book) {
        System.out.println("CLIENT | DEBUG INFO: Sending create request" + book);
        sendMessage(Operation.ADD_BOOK,  book);
        return receiveMessageBoolean();
    }

    /**
     * Sends a request to the server to update a book.
     *
     * @param book the book to update
     * @return true if the book was updated, false otherwise
     */
    public Boolean updateBook(Book book) {
        sendMessage(Operation.UPDATE_BOOK, book);
        return receiveMessageBoolean();
    }

    /**
     * Sends a request to the server to read a book.
     * 
     * @param id the id of the book to read
     * @return the book with the given id
     */
    public Book readBook(int id) {
        sendMessage(Operation.GET_BOOK, id);
        return receiveMessageBook();
    }
    
    /**
     * Sends a request to the server to get all books.
     * 
     * @return a list of all books
     */
    public List<Book> getBooks() {
        sendMessage(Operation.GET_BOOKS, null);
        return receiveMessageBooks();
    }
    
    /**
     * Sends a request to the server to delete a book.
     * 
     * @param id the book to delete
     * @return true if the book was created, false otherwise
     */
    public Boolean deleteBook(int id) {
        sendMessage(Operation.REMOVE_BOOK, id);
        return receiveMessageBoolean();
    }

    
    /**
     * Closes the client socket and streams.
     */
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    /**
     * Sends a message to the server.
     *
     * @param op the operation to perform
     * @param msg the message to send
     */
    private void sendMessage(Operation op, Object msg) {
        Message temp = null;
        if(op == Operation.ADD_BOOK) temp = new CreateMessage(msg);
        if(op == Operation.GET_BOOK) temp = new ReadMessage(msg);
        if(op == Operation.UPDATE_BOOK) temp = new UpdateMessage(msg);
        if(op == Operation.REMOVE_BOOK) temp = new DeleteMessage(msg);
        if(op == Operation.GET_BOOKS) temp = new RefreshMessage();
        if(op == Operation.SEARCH_BY_ALL) temp = new SearchByMessage(Operation.SEARCH_BY_ALL, (Book) msg);
        if(op == Operation.SEARCH_BY_TITLE) temp = new SearchByMessage(Operation.SEARCH_BY_TITLE, (Book) msg);
        if(op == Operation.SEARCH_BY_AUTHOR) temp = new SearchByMessage(Operation.SEARCH_BY_AUTHOR, (Book) msg);
        if(op == Operation.SEARCH_BY_GENRE) temp = new SearchByMessage(Operation.SEARCH_BY_GENRE, (Book) msg);
        if(op == Operation.SEARCH_BY_YEAR) temp = new SearchByMessage(Operation.SEARCH_BY_YEAR, (Book) msg);
        if(op == Operation.SEARCH_BY_TITLE_AUTHOR) temp = new SearchByMessage(Operation.SEARCH_BY_TITLE_AUTHOR, (Book) msg);
        if(op == Operation.SEARCH_BY_TITLE_GENRE) temp = new SearchByMessage(Operation.SEARCH_BY_TITLE_GENRE, (Book) msg);
        if(op == Operation.SEARCH_BY_TITLE_YEAR) temp = new SearchByMessage(Operation.SEARCH_BY_TITLE_YEAR, (Book) msg);
        if(op == Operation.SEARCH_BY_AUTHOR_GENRE) temp = new SearchByMessage(Operation.SEARCH_BY_AUTHOR_GENRE, (Book) msg);
        if(op == Operation.SEARCH_BY_AUTHOR_YEAR) temp = new SearchByMessage(Operation.SEARCH_BY_AUTHOR_YEAR, (Book) msg);
        if(op == Operation.SEARCH_BY_GENRE_YEAR) temp = new SearchByMessage(Operation.SEARCH_BY_GENRE_YEAR, (Book) msg);
        if(op == Operation.SEARCH_BY_TITLE_AUTHOR_GENRE) temp = new SearchByMessage(Operation.SEARCH_BY_TITLE_AUTHOR_GENRE, (Book) msg);
        if(op == Operation.SEARCH_BY_TITLE_AUTHOR_YEAR) temp = new SearchByMessage(Operation.SEARCH_BY_TITLE_AUTHOR_YEAR, (Book) msg);
        if(op == Operation.SEARCH_BY_TITLE_GENRE_YEAR) temp = new SearchByMessage(Operation.SEARCH_BY_TITLE_GENRE_YEAR, (Book) msg);
        if(op == Operation.SEARCH_BY_AUTHOR_GENRE_YEAR) temp = new SearchByMessage(Operation.SEARCH_BY_AUTHOR_GENRE_YEAR, (Book) msg);

        try {
            out.writeObject(temp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Searches for books by the given parameters.
     *
     * @param choice the choice of search
     * @param title the title of the book
     * @param author the author of the book
     * @param year the year of the book
     * @param genre the genre of the book
     * @return a list of books that match the search criteria
     */
    public List<Book> searchBooksBy(int choice, String title, String author, int year, Genre genre) {
        Book tmp = new Book(title, author, year, genre);
        if (choice == 0) sendMessage(Operation.SEARCH_BY_ALL, tmp);
        if (choice == 1) sendMessage(Operation.SEARCH_BY_TITLE, tmp);
        if (choice == 2) sendMessage(Operation.SEARCH_BY_AUTHOR, tmp);
        if (choice == 3) sendMessage(Operation.SEARCH_BY_GENRE, tmp);
        if (choice == 4) sendMessage(Operation.SEARCH_BY_YEAR, tmp);
        if (choice == 5) sendMessage(Operation.SEARCH_BY_AUTHOR_GENRE, tmp);
        if (choice == 6) sendMessage(Operation.SEARCH_BY_TITLE_GENRE, tmp);
        if (choice == 7) sendMessage(Operation.SEARCH_BY_TITLE_AUTHOR, tmp);
        if (choice == 8) sendMessage(Operation.SEARCH_BY_TITLE_YEAR, tmp);
        if (choice == 9) sendMessage(Operation.SEARCH_BY_AUTHOR_YEAR, tmp);
        if (choice == 10) sendMessage(Operation.SEARCH_BY_GENRE_YEAR, tmp);
        if (choice == 11) sendMessage(Operation.SEARCH_BY_TITLE_AUTHOR_GENRE, tmp);
        if (choice == 12) sendMessage(Operation.SEARCH_BY_TITLE_AUTHOR_YEAR, tmp);
        if (choice == 13) sendMessage(Operation.SEARCH_BY_TITLE_GENRE_YEAR, tmp);
        if (choice == 14) sendMessage(Operation.SEARCH_BY_AUTHOR_GENRE_YEAR, tmp);
        if (choice == 15) sendMessage(Operation.GET_BOOKS, null);

        System.out.println("CLIENT | DEBUG INFO: Sent search request");
        return receiveMessageBooks();
    }


    /**
     * Receives a message from the server.
     *
     * @return the message received as a List of Books
     */
    private List<Book> receiveMessageBooks() {
        Message m = null;
        System.out.println("CLIENT | DEBUG INFO: Receiving message...");
        try {
            m = (Message) in.readObject();
            System.out.println("CLIENT | DEBUG INFO: Received message: " + m.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(m.getOperation() == Operation.GET_BOOKS) return (List<Book>) m.getMessage();
        return null;
    }


    /**
     * Receives a message from the server.
     *
     * @return the message received as a Book
     */
    private Book receiveMessageBook() {
        Message m = null;
        try {
            m = (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(m.getOperation() == Operation.RESP_BOOK) return (Book) m.getMessage();
        return null;
    }


    /**
     * Receives a message from the server.
     *
     * @return the message received as a Boolean
     */
    private Boolean receiveMessageBoolean() {
        Message m = null;
        try {
            m = (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(m.getOperation() == Operation.GENERIC_RESPONSE) return (Boolean) m.getMessage();
        return null;
    }
}