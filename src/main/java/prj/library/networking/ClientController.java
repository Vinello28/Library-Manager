package prj.library.networking;

import prj.library.models.Book;
import prj.library.models.Customer;
import prj.library.models.Genre;
import prj.library.models.Lends;
import prj.library.networking.messages.Operation;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * The ClientController class is responsible for handling the communication between the client and the server.
 */
public class ClientController implements ClientControllerInterface {
    private Client client;

    public ClientController() throws IOException {
        client = new Client();
    }

    public Boolean createBook(Book book) {
        System.out.println("CLIENT | DEBUG INFO: Sending create request" + book);
        client.sendMessage(Operation.ADD_BOOK,  book);
        return client.receiveMessageBoolean();
    }

    public Boolean updateBook(Book book) {
        client.sendMessage(Operation.UPDATE_BOOK, book);
        return client.receiveMessageBoolean();
    }

    public Book readBook(int id) {
        Book book = new Book(id, "", "", 0, Genre.Genre, 0);
        client.sendMessage(Operation.GET_BOOK, book);
        return client.receiveMessageBook();
    }

    public List<Book> getBooks() {
        client.sendMessage(Operation.GET_BOOKS, null);
        return client.receiveMessageBooks();
    }

    public Boolean deleteBook(int id) {
        Book book = new Book(id, "", "", 0, Genre.Genre, 0);
        client.sendMessage(Operation.REMOVE_BOOK, book);
        return client.receiveMessageBoolean();
    }

    public List<Book> searchBooksBy(int choice, Book tmp) {
        System.out.println("CLIENT | DEBUG INFO: now choice is " + choice);
        Operation m = choseSearchOpBooks(choice);

        System.out.println("CLIENT | DEBUG INFO: Sending search request" + m + " " + tmp);
        client.sendMessage(m, tmp);
        return client.receiveMessageBooks();
    }

    public Boolean createLend(Lends lend) {
        client.sendMessage(Operation.ADD_LEND, lend);
        return client.receiveMessageBoolean();
    }

    public Boolean updateLend(Lends lend) {
        client.sendMessage(Operation.UPDATE_LEND, lend);
        return client.receiveMessageBoolean();
    }

    public Lends readLend(Lends id) {
        client.sendMessage(Operation.GET_LEND, id);
        return client.receiveMessageLend();
    }

    public List<Lends> getLends() {
        client.sendMessage(Operation.GET_LENDS, null);
        return client.receiveMessageLends();
    }

    public Boolean deleteLend(Lends lend) {
        client.sendMessage(Operation.REMOVE_LEND, lend);
        return client.receiveMessageBoolean();
    }

    public List<Lends> searchLendsBy(int choice, String title, LocalDate returnDate, String cell) {
        Book book = new Book(0, title, "", 0, Genre.Genre, 0);
        Customer customer = new Customer(0, "", "", "", cell);
        List<Customer> c = searchCustomersBy(2, customer);
        List<Book> b = searchBooksBy(1, book);

        if(b.isEmpty()) book = new Book(0, "", "", 0, Genre.Genre, 0);
        else book = b.get(0);
        if (c.isEmpty()) customer = new Customer(0, "", "", "", "");
        else customer = c.get(0);

        Lends tmp = new Lends(book.getId(), customer.getId(), returnDate);

        Operation m = null;
        if (choice == 0 && !b.isEmpty() && !c.isEmpty()) m = Operation.SEARCH_LEND_BY_ALL;
        if (choice == 1 && !b.isEmpty()) m = Operation.SEARCH_LEND_BY_BOOK;
        if (choice == 2) m = Operation.SEARCH_LEND_BY_RETURN_DATE;
        if (choice == 3 && !c.isEmpty()) m = Operation.SEARCH_LEND_BY_CELL;

        if(m == null) {
            m = Operation.GET_LENDS;
            tmp = null;
        }

        System.out.println("CLIENT | DEBUG INFO: Sending search request" + m + " " + tmp);

        client.sendMessage(m, tmp);
        return client.receiveMessageLends();
    }

    public List<Lends> refreshLends() {
        client.sendMessage(Operation.REFRESH_LENDS, null);
        return client.receiveMessageLends();
    }

    public Boolean createCustomer(Customer customer) {
        client.sendMessage(Operation.ADD_CUSTOMER, customer);
        return client.receiveMessageBoolean();
    }

    public Boolean updateCustomer(Customer customer) {
        client.sendMessage(Operation.UPDATE_CUSTOMER, customer);
        return client.receiveMessageBoolean();
    }

    public Customer readCustomer(int id) {
        Customer customer = new Customer(id, "", "", "", "");
        client.sendMessage(Operation.GET_CUSTOMER, customer);
        return client.receiveMessageCustomer();
    }

    public List<Customer> getCustomers() {
        client.sendMessage(Operation.GET_CUSTOMERS, null);
        return client.receiveMessageCustomers();
    }

    public Boolean deleteCustomer(Customer customer) {
        client.sendMessage(Operation.REMOVE_CUSTOMER, customer);
        return client.receiveMessageBoolean();
    }

    public List<Customer> searchCustomersBy(int choice, Customer tmp) {
        Operation m = choseSearchOpCustomers(choice);
        client.sendMessage(m, tmp);
        return client.receiveMessageCustomers();
    }

    /**
     * Choses the search operation based on the choice.
     * @param choice the choice of search
     */
    private Operation choseSearchOpBooks(int choice){
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
     * Choses the search operation based on the choice.
     * @param choice the choice of search
     */
    private Operation choseSearchOpCustomers(int choice){
        if (choice == 0) return Operation.SEARCH_CUSTOMER_BY_ALL;
        if (choice == 1) return Operation.SEARCH_CUSTOMER_BY_NAME;
        if (choice == 2) return Operation.SEARCH_CUSTOMER_BY_PHONE;
        if (choice == 3) return Operation.SEARCH_CUSTOMER_BY_EMAIL;
        if (choice == 4) return Operation.SEARCH_CUSTOMER_BY_ADDRESS;
        if (choice == 5) return Operation.SEARCH_CUSTOMER_BY_NAME_PHONE;
        if (choice == 6) return Operation.SEARCH_CUSTOMER_BY_NAME_EMAIL;
        if (choice == 7) return Operation.SEARCH_CUSTOMER_BY_NAME_ADDRESS;
        if (choice == 8) return Operation.SEARCH_CUSTOMER_BY_PHONE_EMAIL;
        if (choice == 9) return Operation.SEARCH_CUSTOMER_BY_PHONE_ADDRESS;
        if (choice == 10) return Operation.SEARCH_CUSTOMER_BY_EMAIL_ADDRESS;
        if (choice == 11) return Operation.SEARCH_CUSTOMER_BY_NAME_PHONE_EMAIL;
        if (choice == 12) return Operation.SEARCH_CUSTOMER_BY_NAME_PHONE_ADDRESS;
        if (choice == 13) return Operation.SEARCH_CUSTOMER_BY_NAME_EMAIL_ADDRESS;
        if (choice == 14) return Operation.SEARCH_CUSTOMER_BY_PHONE_EMAIL_ADDRESS;
        return Operation.GET_CUSTOMERS;
    }

    /**
     * Closes the connection to the server.
     */
    public void close() {
        client.close();
    }

}
