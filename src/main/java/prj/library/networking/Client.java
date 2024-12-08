package prj.library.networking;

import prj.library.models.Book;
import prj.library.models.Customer;
import prj.library.models.Lends;
import prj.library.networking.messages.*;
import java.io.*;
import java.net.*;
import java.util.List;
import prj.library.networking.NI.NetworkInterface;

public class Client extends NetworkInterface {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 60129;

    /**
     * Constructor for the Client class.
     *
     * @throws IOException if an I/O error occurs when creating the socket
     */
    public Client () throws IOException {
        super(new Socket(SERVER_IP, SERVER_PORT));
    }

    /**
     * Sends a message to the server.
     *
     * @param op the operation to perform
     * @param msg the message to send
     */
    public void sendMessage(Operation op, Object msg) {
        send(MessageFactory.createMessage(op, msg));
    }

    /**
     * receives a message object from the server.
     * @return the message object casted to correct MessageClass
     */
    private Message receiveMessage() {
        Message m = (Message) receive();
        return MessageFactory.createMessage(m.getOperation(), m.getMessage());
    }

    /**
     * Receives a message from the server.
     *
     * @return the message received as a List of Books
     */
    public List<Book> receiveMessageBooks() {
        BooksListMessage m = (BooksListMessage) receiveMessage();
        System.out.println("CLIENT | DEBUG INFO: Received message " + m.getOperation().toString());
        return m.getBooks();
    }


    /**
     * Receives a message from the server.
     *
     * @return the message received as a Book
     */
    public Book receiveMessageBook() {
        BookMessage m = (BookMessage) receiveMessage();
        return m.getBook();
    }


    /**
     * Receives a message from the server.
     *
     * @return the message received as a Boolean
     */
    public Boolean receiveMessageBoolean() {
        GenericMessage m = (GenericMessage) receiveMessage();
        return m.getResponseBoolean();
    }

    /**
     * Receives a generic message from the server.
     *
     * @return the message received as a Int
     */
    public int receiveMessageInt() {
        GenericMessage m = (GenericMessage) receiveMessage();
        return m.getResponseInt();
    }


    /**
     * Receives a message from the server.
     *
     * @return the message received as a Date
     */
    public Lends receiveMessageLend() {
        LendMessage m = (LendMessage) receiveMessage();
        return m.getLend();
    }

    /**
     * Receives a message from the server.
     *
     * @return the message received as a List of Lends
     */
    public List<Lends> receiveMessageLends() {
        LendsListMessage m = (LendsListMessage) receiveMessage();
        return m.getLends();
    }

    /**
     * Receives a message from the server.
     *
     * @return the message received as a List of Customers
     */
    public List<Customer> receiveMessageCustomers() {
        CustomersListMessage m = (CustomersListMessage) receiveMessage();
        return m.getCustomers();
    }

    /**
     * Receives a message from the server.
     *
     * @return the message received as a Customer
     */
    public Customer receiveMessageCustomer() {
        CustomerMessage m = (CustomerMessage) receiveMessage();
        return m.getCustomer();
    }
}