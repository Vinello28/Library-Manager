package prj.library.networking;

import prj.library.models.Book;
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
    public Boolean createBook(Book book) throws IOException, ClassNotFoundException {
        //out.writeObject(new Book(0, book.getTitle(), book.getAuthor(), book.getYear()));
        sendMessage(Operation.ADD_BOOK, book);
        return (Boolean) ReceiveMessage();
    }

    /**
     * Sends a request to the server to update a book.
     *
     * @param book the book to update
     * @return true if the book was updated, false otherwise
     */
    public Boolean updateBook(Book book) throws IOException, ClassNotFoundException {
        //out.writeObject(new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getYear()));
        sendMessage(Operation.UPDATE_BOOK, book);
        return (Boolean) ReceiveMessage();
    }

    /**
     * Sends a request to the server to read a book.
     * 
     * @param id the id of the book to read
     * @return the book with the given id
     */
    public Book readBook(int id) throws IOException, ClassNotFoundException {
        //out.writeObject(new Book(id, "", "", 0));
        sendMessage(Operation.GET_BOOK, id);
        return (Book) ReceiveMessage();
    }
    
    /**
     * Sends a request to the server to get all books.
     * 
     * @return a list of all books
     */
    public List<Book> getBooks() throws IOException, ClassNotFoundException {
        //out.writeObject("GET_ALL");
        sendMessage(Operation.GET_BOOKS, null);
        return (List<Book>) ReceiveMessage();
    }
    
    /**
     * Sends a request to the server to delete a book.
     * 
     * @param id the book to delete
     * @return true if the book was created, false otherwise
     */
    public Boolean deleteBook(int id) throws IOException, ClassNotFoundException {
        //out.writeObject(new Book(id, "", "", 0));
        sendMessage(Operation.REMOVE_BOOK, id);
        return (Boolean) ReceiveMessage();
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
    private void sendMessage(Operation op, Object msg) throws IOException {
        Message temp = null;
        if(op == Operation.ADD_BOOK) temp = new CreateMessage(msg);
        if(op == Operation.GET_BOOK) temp = new ReadMessage(msg);
        if(op == Operation.UPDATE_BOOK) temp = new UpdateMessage(msg);
        if(op == Operation.REMOVE_BOOK) temp = new DeleteMessage(msg);
        if(op == Operation.GET_BOOKS) temp = new RefreshMessage();

        out.writeObject(temp);
    }


    /**
     * Receives a message from the server.
     * @return the message received
     */
    private Object ReceiveMessage() throws IOException, ClassNotFoundException {
        Message m = (Message) in.readObject();
        if(m.getOperation() == Operation.RESP_BOOK) return (Book) m.getMessage();
        if(m.getOperation() == Operation.GET_BOOKS) return (ArrayList<Book>) m.getMessage();
        if(m.getOperation() == Operation.GENERIC_RESPONSE) return (Boolean) m.getMessage();
        return null;
    }
}