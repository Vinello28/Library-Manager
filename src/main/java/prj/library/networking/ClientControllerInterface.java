package prj.library.networking;

import prj.library.models.Book;
import prj.library.models.Customer;
import prj.library.models.Genre;
import prj.library.models.Lends;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Interface for the client controller.
 */
public interface ClientControllerInterface {

    /**
     * Sends a request to the server to create a book.
     *
     * @param book the book to create
     * @return true if the book was created, false otherwise
     */
    Boolean createBook(Book book);

    /**
     * Sends a request to the server to update a book.
     *
     * @param book the book to update
     * @return true if the book was updated, false otherwise
     */
    Boolean updateBook(Book book);

    /**
     * Sends a request to the server to read a book.
     *
     * @param id the id of the book to read
     * @return the book with the given id
     */
    Book readBook(int id);

    /**
     * Sends a request to the server to get all books.
     *
     * @return a list of all books
     */
    List<Book> getBooks();

    /**
     * Sends a request to the server to delete a book.
     *
     * @param id the book to delete
     * @return true if the book was created, false otherwise
     */
    Boolean deleteBook(int id);

    /**
     * Searches for books by the given parameters.
     *
     * @param choice the choice of search
     * @param tmp the book to search for
     * @return a list of books that match the search criteria
     */
    List<Book> searchBooksBy(int choice, Book tmp);

    /**
     * Sends a request to the server to create a lend.
     *
     * @param lend the lend to create
     * @return true if the lend was created, false otherwise
     */
    Boolean createLend(Lends lend);

    /**
     * Sends a request to the server to update a lend.
     *
     * @param lend the lend to update
     * @return true if the lend was updated, false otherwise
     */
    Boolean updateLend(Lends lend);

    /**
     * Sends a request to the server to read a lend.
     *
     * @param id the id of the lend to read
     * @return the lend with the given id
     */
    Lends readLend(Lends id);

    /**
     * Sends a request to the server to get all lends.
     *
     * @return a list of all lends
     */
    List<Lends> getLends();

    /**
     * Sends a request to the server to delete a lend.
     *
     * @param lend the lend to delete
     * @return true if the lend was created, false otherwise
     */
    Boolean deleteLend(Lends lend);

    /**
     * Searches for lends by the given parameters.
     * @param choice the choice of search
     * @param b_id the book id
     * @param returnDate the return date
     * @param c_id the customer id
     * @param returned the returned status
     * @param sentinel the sentinel status
     * @return a list of lends that match the search criteria
     */
    List<Lends> searchLendsBy(int choice, int b_id, LocalDate returnDate, int c_id, Boolean returned, Boolean sentinel);

    /**
     * Sends a request to the server to create a customer.
     *
     * @param customer the customer to create
     * @return true if the customer was created, false otherwise
     */
    Boolean createCustomer(Customer customer);

    /**
     * Sends a request to the server to update a customer.
     *
     * @param customer the customer to update
     * @return true if the customer was updated, false otherwise
     */
    Boolean updateCustomer(Customer customer);

    /**
     * Sends a request to the server to read a customer.
     *
     * @param id the id of the customer to read
     * @return the customer with the given id
     */
    Customer readCustomer(int id);

    /**
     * Sends a request to the server to get all customers.
     *
     * @return a list of all customers
     */
    List<Customer> getCustomers();

    /**
     * Sends a request to the server to delete a customer.
     * @param customer the customer to delete
     * @return true if the customer was created, false otherwise
     */
    Boolean deleteCustomer(Customer customer);

    /**
     * Searches for customers by the given parameters.
     *
     * @param choice the choice of search
     * @param customer the customer to search for
     * @return a list of customers that match the search criteria
     */
    List<Customer> searchCustomersBy(int choice, Customer customer);

    /**
     * Calculates the number of lends for each genre.
     * @return a map with the genre and the number of lends
     */
     Map<Genre, Long> calculateGenreLendingStats();

    /**
     * Calculates the number of lends for each customer.
     * @return a map with the customer and the number of lends
     */
     Map<Customer, Integer> calculateCustomerLendingStats();

     /**
     * Calculates the number of lends not returned.
      * @return the number of lends not returned
     */
     int getNotReturnedLendsCount();

    /**
     * Sends a request to the server to send alerts to customers.
     * @return the status of the operation
     */
     Boolean sendAlerts();
}
