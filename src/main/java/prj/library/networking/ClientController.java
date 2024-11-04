package prj.library.networking;

import prj.library.models.Book;
import prj.library.models.Customer;
import prj.library.models.Genre;
import prj.library.models.Lends;
import prj.library.networking.messages.Message;
import prj.library.networking.messages.MessageFactory;
import prj.library.networking.messages.Operation;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ClientController {
    private Client client;

    public ClientController() throws IOException {
        client = new Client();
    }

    /**
     * Sends a request to the server to create a book.
     *
     * @param book the book to create
     * @return true if the book was created, false otherwise
     */
    public Boolean createBook(Book book) {
        System.out.println("CLIENT | DEBUG INFO: Sending create request" + book);
        client.sendMessage(Operation.ADD_BOOK,  book);
        return client.receiveMessageBoolean();
    }

    /**
     * Sends a request to the server to update a book.
     *
     * @param book the book to update
     * @return true if the book was updated, false otherwise
     */
    public Boolean updateBook(Book book) {
        client.sendMessage(Operation.UPDATE_BOOK, book);
        return client.receiveMessageBoolean();
    }

    /**
     * Sends a request to the server to read a book.
     *
     * @param id the id of the book to read
     * @return the book with the given id
     */
    public Book readBook(int id) {
        Book book = new Book(id, "", "", 0, Genre.Genre, 0);
        client.sendMessage(Operation.GET_BOOK, book);
        return client.receiveMessageBook();
    }

    /**
     * Sends a request to the server to get all books.
     *
     * @return a list of all books
     */
    public List<Book> getBooks() {
        client.sendMessage(Operation.GET_BOOKS, null);
        return client.receiveMessageBooks();
    }

    /**
     * Sends a request to the server to delete a book.
     *
     * @param id the book to delete
     * @return true if the book was created, false otherwise
     */
    public Boolean deleteBook(int id) {
        Book book = new Book(id, "", "", 0, Genre.Genre, 0);
        client.sendMessage(Operation.REMOVE_BOOK, book);
        return client.receiveMessageBoolean();
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
    public List<Book> searchBooksBy(int choice, Book tmp) {
        System.out.println("CLIENT | DEBUG INFO: now choice is " + choice);
        Operation m = choseSearchOp(choice);

        System.out.println("CLIENT | DEBUG INFO: Sending search request" + m + " " + tmp);
        client.sendMessage(m, tmp);
        return client.receiveMessageBooks();
    }

    private Operation choseSearchOp(int choice){
        if (choice == 0) return Operation.SEARCH_BY_ALL;
        if (choice == 1) return Operation.SEARCH_BY_TITLE;
        if (choice == 2) return Operation.SEARCH_BY_AUTHOR;
        if (choice == 3) return Operation.SEARCH_BY_YEAR;
        if (choice == 4) return Operation.SEARCH_BY_GENRE;
        if (choice == 5) return Operation.SEARCH_BY_TITLE_AUTHOR;
        if (choice == 6) return Operation.SEARCH_BY_TITLE_YEAR;
        if (choice == 7) return Operation.SEARCH_BY_TITLE_GENRE;
        if (choice == 8) return Operation.SEARCH_BY_AUTHOR_YEAR;
        if (choice == 9) return Operation.SEARCH_BY_AUTHOR_GENRE;
        if (choice == 10) return Operation.SEARCH_BY_GENRE_YEAR;
        if (choice == 11) return Operation.SEARCH_BY_TITLE_AUTHOR_YEAR;
        if (choice == 12) return Operation.SEARCH_BY_TITLE_AUTHOR_GENRE;
        if (choice == 13) return Operation.SEARCH_BY_TITLE_GENRE_YEAR;
        if (choice == 14) return Operation.SEARCH_BY_AUTHOR_GENRE_YEAR;
        if (choice == 15) return Operation.GET_BOOKS;
        return null;
    }

    /**
     * Sends a request to the server to create a lend.
     *
     * @param lend the lend to create
     * @return true if the lend was created, false otherwise
     */
    public Boolean createLend(Lends lend) {
        client.sendMessage(Operation.ADD_LEND, lend);
        return client.receiveMessageBoolean();
    }

    /**
     * Sends a request to the server to update a lend.
     *
     * @param lend the lend to update
     * @return true if the lend was updated, false otherwise
     */
    public Boolean updateLend(Lends lend) {
        client.sendMessage(Operation.UPDATE_LEND, lend);
        return client.receiveMessageBoolean();
    }

    /**
     * Sends a request to the server to read a lend.
     *
     * @param id the id of the lend to read
     * @return the lend with the given id
     */
    public Lends readLend(Lends id) {
        client.sendMessage(Operation.GET_LEND, id);
        return client.receiveMessageLend();
    }

    /**
     * Sends a request to the server to get all lends.
     *
     * @return a list of all lends
     */
    public List<Lends> getLends() {
        client.sendMessage(Operation.GET_LENDS, null);
        return client.receiveMessageLends();
    }

    /**
     * Sends a request to the server to delete a lend.
     *
     * @param id the lend to delete
     * @return true if the lend was created, false otherwise
     */
    public Boolean deleteLend(int id) {
        Lends lend = new Lends(id, 0, null);
        client.sendMessage(Operation.REMOVE_LEND, lend);
        return client.receiveMessageBoolean();
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
        Operation m = null;
        if (choice == 0) m = Operation.SEARCH_LEND_BY_ALL;
        if (choice == 1) m = Operation.SEARCH_LEND_BY_BOOK;
        if (choice == 2) m = Operation.SEARCH_LEND_BY_RETURN_DATE;
        else m = Operation.GET_LENDS;

        if(m == Operation.GET_LENDS) tmp = null;

        client.sendMessage(m, tmp);
        return client.receiveMessageLends();
    }

    /**
     * Refreshes the list of lends.
     *
     * @return a list of all lends
     */
    public List<Lends> refreshLends() {
        client.sendMessage(Operation.REFRESH_LENDS, null);
        return client.receiveMessageLends();
    }

    /**
     * Sends a request to the server to create a customer.
     *
     * @param customer the customer to create
     * @return true if the customer was created, false otherwise
     */
    public Boolean createCustomer(Customer customer) {
        client.sendMessage(Operation.ADD_CUSTOMER, customer);
        return client.receiveMessageBoolean();
    }

    /**
     * Sends a request to the server to update a customer.
     *
     * @param customer the customer to update
     * @return true if the customer was updated, false otherwise
     */
    public Boolean updateCustomer(Customer customer) {
        client.sendMessage(Operation.UPDATE_CUSTOMER, customer);
        return client.receiveMessageBoolean();
    }

    /**
     * Sends a request to the server to read a customer.
     *
     * @param id the id of the customer to read
     * @return the customer with the given id
     */
    public Customer readCustomer(int id) {
        Customer customer = new Customer(id, "", "", "", "");
        client.sendMessage(Operation.GET_CUSTOMER, customer);
        return client.receiveMessageCustomer();
    }

    /**
     * Sends a request to the server to get all customers.
     *
     * @return a list of all customers
     */
    public List<Customer> getCustomers() {
        client.sendMessage(Operation.GET_CUSTOMERS, null);
        return client.receiveMessageCustomers();
    }

    /**
     * Sends a request to the server to delete a customer.
     *
     * @param id the customer to delete
     * @return true if the customer was created, false otherwise
     */
    public Boolean deleteCustomer(int id) {
        Customer customer = new Customer(id, "", "", "", "");
        client.sendMessage(Operation.REMOVE_CUSTOMER, customer);
        return client.receiveMessageBoolean();
    }

    /**
     * Searches for customers by the given parameters.
     *
     * @param choice the choice of search
     * @param name the name of the customer
     * @param phone the phone of the customer
     * @param email the email of the customer
     * @param address the address of the customer
     * @return a list of customers that match the search criteria
     */
    public List<Customer> searchCustomersBy(int choice, String name, String phone, String email, String address) {
        Customer tmp = new Customer(name, phone, email, address);
        Operation m = null;
        if (choice == 0) m = Operation.SEARCH_CUSTOMER_BY_ALL;
        if (choice == 1) m = Operation.SEARCH_CUSTOMER_BY_NAME;
        if (choice == 2) m = Operation.SEARCH_CUSTOMER_BY_PHONE;
        if (choice == 3) m = Operation.SEARCH_CUSTOMER_BY_EMAIL;
        if (choice == 4) m = Operation.SEARCH_CUSTOMER_BY_ADDRESS;
        if (choice == 5) m = Operation.SEARCH_CUSTOMER_BY_NAME_PHONE;
        if (choice == 6) m = Operation.SEARCH_CUSTOMER_BY_NAME_EMAIL;
        if (choice == 7) m = Operation.SEARCH_CUSTOMER_BY_NAME_ADDRESS;
        else m = Operation.GET_CUSTOMERS;

        if (m == Operation.GET_CUSTOMERS) tmp = null;

        client.sendMessage(m, tmp);
        return client.receiveMessageCustomers();
    }

    /**
     * Closes the connection to the server.
     */
    public void close() {
        client.close();
    }

}
