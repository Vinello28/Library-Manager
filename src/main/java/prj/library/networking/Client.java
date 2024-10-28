package prj.library.networking;

import prj.library.models.Book;
import prj.library.models.Genre;
import prj.library.models.Lends;
import prj.library.networking.messages.*;

import java.io.*;
import java.net.*;
import java.util.Date;
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
        Book book = new Book(id, "", "", 0, Genre.Genre, 0);
        sendMessage(Operation.GET_BOOK, book);
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
        Book book = new Book(id, "", "", 0, Genre.Genre, 0);
        sendMessage(Operation.REMOVE_BOOK, book);
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
        try {
            out.writeObject(MessageFactory.createMessage(op, msg));
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
        try {
            m = (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("CLIENT | DEBUG INFO: Received message " + m.getOperation().toString());
        if (m.getOperation() == Operation.GET_BOOKS) return (List<Book>) m.getMessage();
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

    /**
     * Receives a message from the server.
     *
     * @return the message received as a Date
     */
    private Lends receiveMessageLend() {
        Message m = null;
        try {
            m = (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(m.getOperation() == Operation.RESP_LEND) return (Lends) m.getMessage();
        return null;
    }

    /**
     * Receives a message from the server.
     *
     * @return the message received as a List of Lends
     */
    private List<Lends> receiveMessageLends() {
        Message m = null;
        try {
            m = (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("CLIENT | DEBUG INFO: Received message " + m.getOperation().toString() + " contents -> " + m.getMessage());

        if (m.getOperation() == Operation.REFRESH_LENDS) return (List<Lends>) m.getMessage();
        return null;
    }

    /**
     * Sends a request to the server to create a lend.
     *
     * @param lend the lend to create
     * @return true if the lend was created, false otherwise
     */
    public Boolean createLend(Lends lend) {
        sendMessage(Operation.ADD_LEND, lend);
        return receiveMessageBoolean();
    }

    /**
     * Sends a request to the server to update a lend.
     *
     * @param lend the lend to update
     * @return true if the lend was updated, false otherwise
     */
    public Boolean updateLend(Lends lend) {
        sendMessage(Operation.UPDATE_LEND, lend);
        return receiveMessageBoolean();
    }

    /**
     * Sends a request to the server to read a lend.
     *
     * @param id the id of the lend to read
     * @return the lend with the given id
     */
    public Lends readLend(Lends id) {
        sendMessage(Operation.GET_LEND, id);
        return receiveMessageLend();
    }

    /**
     * Sends a request to the server to get all lends.
     *
     * @return a list of all lends
     */
    public List<Lends> getLends() {
        sendMessage(Operation.GET_LENDS, null);
        return receiveMessageLends();
    }

    /**
     * Sends a request to the server to delete a lend.
     *
     * @param id the lend to delete
     * @return true if the lend was created, false otherwise
     */
    public Boolean deleteLend(int id) {
        sendMessage(Operation.REMOVE_LEND, id);
        return receiveMessageBoolean();
    }

    /**
     * Searches for lends by the given parameters.
     *
     * @param choice the choice of search
     * @param book the book of the lend
     * @param returnDate the return date of the lend
     * @return a list of lends that match the search criteria
     */
    public List<Lends> searchLendsBy(int choice, Book book, Date returnDate) {
        Lends tmp = new Lends(book, returnDate);
        if (choice == 0) sendMessage(Operation.SEARCH_LEND_BY_ALL, tmp);
        if (choice == 1) sendMessage(Operation.SEARCH_LEND_BY_BOOK, tmp);
        if (choice == 2) sendMessage(Operation.SEARCH_LEND_BY_RETURN_DATE, tmp);

        System.out.println("CLIENT | DEBUG INFO: Sent search request");
        return receiveMessageLends();
    }

    /**
     * Refreshes the list of lends.
     *
     * @return a list of all lends
     */
    public List<Lends> refreshLends() {
        sendMessage(Operation.REFRESH_LENDS, null);
        return receiveMessageLends();
    }
}