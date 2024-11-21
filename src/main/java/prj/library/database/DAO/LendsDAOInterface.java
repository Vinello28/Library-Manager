package prj.library.database.DAO;

import prj.library.models.Lends;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for the LendsDAO class
 */
public interface LendsDAOInterface {

    /**
     * Create a new lend
     * @param lend the lend to be created
     */
    void createLend(Lends lend);

    /**
     * Update a lend
     * @param lend the lend to be updated
     */
    void updateLend(Lends lend);

    /**
     * Delete a lend
     * @param lend the lend to be deleted
     */
    void deleteLend(Lends lend);

    /**
     * Get a lend by its id
     * @param id the id of the lend
     * @return the lend with the given id
     */
    Lends readLend(int id);

    /**
     * Get all lends
     * @return a list of all lends
     */
    List<Lends> getLends();

    /**
     * Get all lends that have been returned or not
     * @param returned the return status of the lend
     * @return a list of all lends with specified return status
     */
    List<Lends> getLendsReturned(Boolean returned);

    /**
     * Get all lends by a customer id
     * @param customerId the id of the customer
     * @return a list of all lends by the customer with the given id
     */
    List<Lends> getLendsByCustomerId(int customerId);

    /**
     * Get all lends by a book id
     * @param bookId the id of the book
     * @return a list of all lends by the book with the given id
     */
    List<Lends> getLendsByBookId(int bookId);

    /**
     * Get all late lends
     * @return a list of all late lends
     */
    List<Lends> getLateLends();

    /**
     * Get Lends by return date
     * @param returnDate the return date of the lend
     * @return a list of all lends with the given return date
     */
    List<Lends> getLendsByReturnDate(LocalDate returnDate);

    /**
     * Get lends by all parameters including returned
     * @param bookId the id of the book
     * @param customerId the id of the customer
     * @param returnDate the return date of the lend
     * @param returned the return status of the lend
     * @return a list of all lends that match the given parameters
     */
    List<Lends> getLendsByAllReturned(int bookId, int customerId, LocalDate returnDate, boolean returned);

    /**
     * Get lends by all parameters excluding returned
     * @param bookId the id of the book
     * @param customerId the id of the customer
     * @param returnDate the return date of the lend
     * @return a list of all lends that match the given parameters
     */
    List<Lends> getLendsByAll(int bookId, int customerId, LocalDate returnDate);

    /**
     * Get lends by all parameters excluding return date
     * @param bookId the id of the book
     * @param customerId the id of the customer
     * @param returned the return status of the lend
     * @return a list of all lends that match the given parameters
     */
    List<Lends> getLendsByBookIdCustomerIdReturned(int bookId, int customerId, boolean returned);

    /**
     * Get lends by all parameters excluding customer id
     * @param bookId the id of the book
     * @param returnDate the return date of the lend
     * @param returned the return status of the lend
     * @return a list of all lends that match the given parameters
     */
    List<Lends> getLendsByBookIdReturnDateReturned(int bookId, LocalDate returnDate, boolean returned);

    /**
     * Get lends by all parameters excluding book id
     * @param customerId the id of the customer
     * @param returnDate the return date of the lend
     * @param returned the return status of the lend
     * @return a list of all lends that match the given parameters
     */
    List<Lends> getLendsByCustomerIdReturnDateReturned(int customerId, LocalDate returnDate, boolean returned);

    /**
     * Get lends by book id and customer id
     * @param bookId the id of the book
     * @param customerId the id of the customer
     * @return a list of all lends that match the given parameters
     */
    List<Lends> getLendsByBookIdCustomerId(int bookId, int customerId);

    /**
     * Get lends by book id and return date
     * @param bookId the id of the book
     * @param returnDate the return date of the lend
     * @return a list of all lends that match the given parameters
     */
    List<Lends> getLendsByBookIdReturnDate(int bookId, LocalDate returnDate);

    /**
     * Get lends by book id and returned
     * @param bookId the id of the book
     * @param returned the return status of the lend
     * @return a list of all lends that match the given parameters
     */
    List<Lends> getLendsByBookIdReturned(int bookId, boolean returned);

    /**
     * Get lends by customer id and return date
     * @param customerId the id of the customer
     * @param returnDate the return date of the lend
     * @return a list of all lends that match the given parameters
     */
    List<Lends> getLendsByCustomerIdReturnDate(int customerId, LocalDate returnDate);

    /**
     * Get lends by customer id and returned
     * @param customerId the id of the customer
     * @param returned the return status of the lend
     * @return a list of all lends that match the given parameters
     */
    List<Lends> getLendsByCustomerIdReturned(int customerId, boolean returned);

    /**
     * Get lends by return date and returned
     * @param returnDate the return date of the lend
     * @param returned the return status of the lend
     * @return a list of all lends that match the given parameters
     */
    List<Lends> getLendsByReturnDateReturned(LocalDate returnDate, boolean returned);
}
