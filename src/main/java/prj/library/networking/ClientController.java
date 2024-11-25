package prj.library.networking;

import prj.library.models.Book;
import prj.library.models.Customer;
import prj.library.models.Genre;
import prj.library.models.Lends;
import prj.library.networking.messages.Operation;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Operation m = choseSearchOpBooks(choice);
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

    public List<Lends> searchLateLends() {
        client.sendMessage(Operation.SEARCH_LEND_BY_LATE, null);
        return client.receiveMessageLends();
    }

    public List<Lends> searchLendsBy(int choice, int b_id, LocalDate returnDate, int c_id, Boolean returned, Boolean sentinel) {
        Lends tmp = new Lends(b_id, c_id, returnDate, returned);

        Operation m = null;
        if(!sentinel && !returned) {
            switch (choice) {
                case 0:
                    m = Operation.SEARCH_LEND_BY_ALL;
                    break;
                case 1:
                    m = Operation.SEARCH_LEND_BY_BOOK;
                    break;
                case 2:
                    m = Operation.SEARCH_LEND_BY_CUSTOMER;
                    break;
                case 3:
                    m = Operation.SEARCH_LEND_BY_RETURN_DATE;
                    break;
                case 4:
                    m = Operation.SEARCH_LEND_BY_BOOK_CUSTOMER;
                    break;
                case 5:
                    m = Operation.SEARCH_LEND_BY_BOOK_RETURN_DATE;
                    break;
                case 6:
                    m = Operation.SEARCH_LEND_BY_CUSTOMER_RETURN_DATE;
                    break;
                default:
                    m = Operation.GET_LENDS;
                    break;
            }
        } else {
            switch (choice) {
                case 0:
                    m = Operation.SEARCH_LEND_BY_ALL_RETURNED;
                    break;
                case 1:
                    m = Operation.SEARCH_LEND_BY_BOOK_RETURNED;
                    break;
                case 2:
                    m = Operation.SEARCH_LEND_BY_CUSTOMER_RETURNED;
                    break;
                case 3:
                    m = Operation.SEARCH_LEND_BY_RETURN_DATE_RETURNED;
                    break;
                case 4:
                    m = Operation.SEARCH_LEND_BY_BOOK_CUSTOMER_RETURNED;
                    break;
                case 5:
                    m = Operation.SEARCH_LEND_BY_BOOK_RETURN_DATE_RETURNED;
                    break;
                case 6:
                    m = Operation.SEARCH_LEND_BY_CUSTOMER_RETURN_DATE_RETURNED;
                    break;
                case 7:
                    m = Operation.GET_LENDS_RETURNED;
                    break;
                default:
                    m = Operation.GET_LENDS;
                    break;
            }
        }

        client.sendMessage(m, tmp);
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

    public Map<Genre, Long> calculateGenreLendingStats() {
        List<Lends> allLends = getLends();

        return allLends.stream()
                .map(lend -> readBook(lend.getBookId()))  //obtain the book for each lend
                .filter(book -> book != null)  //filter out null books
                .collect(Collectors.groupingBy(
                        Book::getGenre,  //group by genre
                        Collectors.counting()  //count the number of lends for each genre
                ));
    }

    public Map<Customer, Integer> calculateCustomerLendingStats() {
        Map<Customer, Integer> customerLendCount = new HashMap<>();

        List<Customer> allCustomers = getCustomers();
        for (Customer customer : allCustomers) {
            customerLendCount.put(customer, getLendCountByCustomer(customer));
        }

        return customerLendCount;
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
        if (choice == 6) return Operation.SEARCH_CUSTOMER_BY_NAME_PHONE_EMAIL;
        if (choice == 7) return Operation.SEARCH_CUSTOMER_BY_NAME_EMAIL;
        if (choice == 8) return Operation.SEARCH_CUSTOMER_BY_NAME_ADDRESS;
        if (choice == 9) return Operation.SEARCH_CUSTOMER_BY_PHONE_EMAIL;
        if (choice == 10) return Operation.SEARCH_CUSTOMER_BY_PHONE_ADDRESS;
        if (choice == 11) return Operation.SEARCH_CUSTOMER_BY_EMAIL_ADDRESS;
        if (choice == 12) return Operation.SEARCH_CUSTOMER_BY_NAME_EMAIL_ADDRESS;
        if (choice == 13) return Operation.SEARCH_CUSTOMER_BY_PHONE_EMAIL_ADDRESS;
        if (choice == 14) return Operation.SEARCH_CUSTOMER_BY_NAME_PHONE_ADDRESS;
        return Operation.GET_CUSTOMERS;
    }

    /**
     * Method to calculate the number of lends per customer.
     * @param customer the given customer
     * @return number of lends per customer
     */
    private Integer getLendCountByCustomer(Customer customer) {
        List<Lends> allLends = getLends();

        return Math.toIntExact(allLends.stream()
                .filter(lend -> (lend.getCustomerId() == customer.getId()))
                .count());
    }

    /**
     * Closes the connection to the server.
     */
    public void close() {
        client.close();
    }

}
