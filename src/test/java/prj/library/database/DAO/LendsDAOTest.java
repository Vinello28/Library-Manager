package prj.library.database.DAO;

import org.junit.jupiter.api.*;
import prj.library.models.Book;
import prj.library.models.Customer;
import prj.library.models.Genre;
import prj.library.models.Lends;
import prj.library.notification.VirtualLend;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the LendsDAO class.
 * <p>
 * This class contains unit tests for the LendsDAO class, which handles database operations
 * related to lending books to customers. The tests are ordered using the @TestMethodOrder
 * annotation with the MethodOrderer.OrderAnnotation class.
 * <p>
 * The tests cover the following scenarios:
 * <p>
 * 1. testCreateLend: Tests the creation of a lend entry in the database.<p>
 * 2. testReadLend: Tests reading a lend entry from the database.<p>
 * 3. testUpdateLend: Tests updating a lend entry in the database.<p>
 * 4. testGetLendsByCustomerId: Tests retrieving lends by customer ID.<p>
 * 5. testGetLendsByBookId: Tests retrieving lends by book ID.<p>
 * 6. testGetLateLends: Tests retrieving lends that are late.<p>
 * 7. testGetLendsByReturnDate: Tests retrieving lends by return date.<p>
 * 8. testGetLendsByAll: Tests retrieving lends by all parameters (book ID, customer ID, return date).<p>
 * 9. testDeleteLend: Tests deleting a lend entry from the database.<p>
 * 10. testGetLendsByBookIdCustomerIdReturned: Tests retrieving lends by book ID, customer ID, and returned status.<p>
 * 11. testGetLendsByBookIdReturnDateReturned: Tests retrieving lends by book ID, return date, and returned status.<p>
 * 12. testGetLendsByCustomerIdReturnDateReturned: Tests retrieving lends by customer ID, return date, and returned status.<p>
 * 13. testGetLateLendsNotification: Tests retrieving late lends notifications.<p>
 * <p>
 * The setUp method initializes the test environment by creating instances of LendsDAO, BookDAO, and CustomerDAO,<p>
 * and inserting a test book and customer into the database. The tearDown method cleans up resources by deleting<p>
 * the test book and customer entries from the database.<p>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //Ordering test to avoid database errors
class LendsDAOTest {
    private static LendsDAO lendsDAO;
    private static Lends testLend;
    private static Book book;
    private static Customer customer;

    private static int TEST_BOOK_ID;
    private static int TEST_CUSTOMER_ID;
    private static final LocalDate TEST_RETURN_DATE = LocalDate.now().plusDays(7);
    private static final boolean TEST_RETURNED = false;


    /**
     * Sets up the test environment before all tests are run.<p>
     * Initializes the LendsDAO, BookDAO, and CustomerDAO instances.<p>
     * Creates a test book and a test customer, and inserts them into the database.<p>
     * Retrieves the IDs of the created book and customer for use in tests.<p>
     * Initializes a test lend instance with the retrieved book and customer IDs.<p>
     */
    @BeforeAll
    static void setUp() {
        lendsDAO = new LendsDAO();
        BookDAO bookDAO = new BookDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        book = new Book("Test Book", "Test Author", 2021, Genre.MYSTERY, 1);
        customer = new Customer("Test Customer", "Test Email", "Test Phone", "Test Address");
        bookDAO.createBook(book);
        customerDAO.createCustomer(customer);

        TEST_BOOK_ID = bookDAO.getBooksByAllParam("Test Book", "Test Author", Genre.MYSTERY, 2021).get(0).getId();
        TEST_CUSTOMER_ID = customerDAO.searchCustomerByAll("Test Customer", "Test Phone", "Test Email", "Test Address").get(0).getId();

        testLend = new Lends(TEST_BOOK_ID, TEST_CUSTOMER_ID, TEST_RETURN_DATE, TEST_RETURNED);
    }

    /**
     * Cleans up resources after all tests have been executed.
     * This method deletes the book and customer entries from the database
     * to ensure no test data remains.
     */
    @AfterAll
    static void tearDown() {
        BookDAO bookDAO = new BookDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        bookDAO.deleteBook(book.getId());
        customerDAO.deleteCustomer(customer);
    }

    @Test
    @Order(1)
    void testCreateLend() {
        lendsDAO.createLend(testLend);
        Lends l = lendsDAO.getLendsByAll(TEST_BOOK_ID, TEST_CUSTOMER_ID, TEST_RETURN_DATE).get(0);
        assertNotEquals(0, l.getId(), "Lend ID should be set after creation");
    }

    @Test
    @Order(2)
    void testReadLend() {
        Lends l = lendsDAO.getLendsByAll(TEST_BOOK_ID, TEST_CUSTOMER_ID, TEST_RETURN_DATE).get(0);
        Lends retrievedLend = lendsDAO.readLend(l.getId());

        assertNotNull(retrievedLend, "Retrieved lend should not be null");
        assertEquals(testLend.getBookId(), retrievedLend.getBookId());
        assertEquals(testLend.getCustomerId(), retrievedLend.getCustomerId());
        assertEquals(testLend.getReturnDate(), retrievedLend.getReturnDate());
        assertEquals(testLend.isReturned(), retrievedLend.isReturned());
    }

    @Test
    @Order(3)
    void testUpdateLend() {
        testLend.setReturned(true);

        lendsDAO.updateLend(testLend);
        Lends l = lendsDAO.getLendsByAll(TEST_BOOK_ID, TEST_CUSTOMER_ID, TEST_RETURN_DATE).get(0);
        Lends updatedLend = lendsDAO.readLend(l.getId());

        assertNotNull(updatedLend, "Updated lend should not be null");
        assertTrue(updatedLend.isReturned(), "Lend should be marked as returned");
    }

    @Test
    @Order(4)
    void testGetLendsByCustomerId() {
        List<Lends> customerLends = lendsDAO.getLendsByCustomerId(TEST_CUSTOMER_ID);

        assertFalse(customerLends.isEmpty(), "Customer lends list should not be empty");
        assertTrue(customerLends.stream().anyMatch(l -> l.getCustomerId() == TEST_CUSTOMER_ID && l.getBookId() == TEST_BOOK_ID && l.getReturnDate().equals(TEST_RETURN_DATE)),
                "All lends should belong to test customer");
    }

    @Test
    @Order(5)
    void testGetLendsByBookId() {
        List<Lends> bookLends = lendsDAO.getLendsByBookId(TEST_BOOK_ID);

        assertFalse(bookLends.isEmpty(), "Book lends list should not be empty");
        assertTrue(bookLends.stream().anyMatch(l -> l.getCustomerId() == TEST_CUSTOMER_ID && l.getBookId() == TEST_BOOK_ID && l.getReturnDate().equals(TEST_RETURN_DATE)),
                "All lends should be for test book");
    }

    @Test
    @Order(6)
    void testGetLateLends() {
        Lends lateLend = new Lends(TEST_BOOK_ID, TEST_CUSTOMER_ID,
                LocalDate.now().minusDays(1), false);
        lendsDAO.createLend(lateLend);

        List<Lends> lateLends = lendsDAO.getLateLends();

        assertFalse(lateLends.isEmpty(), "Late lends list should not be empty");
        assertTrue(lateLends.stream().anyMatch(l ->
                        l.getCustomerId() == TEST_CUSTOMER_ID && l.getBookId() == TEST_BOOK_ID && l.getReturnDate().equals(LocalDate.now().minusDays(1))),
                "Should find at least one late and unreturned lend");

        
        lendsDAO.deleteLend(lateLend);
    }

    @Test
    @Order(7)
    void testGetLendsByReturnDate() {
        List<Lends> dateLends = lendsDAO.getLendsByReturnDate(TEST_RETURN_DATE);
        
        assertFalse(dateLends.isEmpty(), "Date lends list should not be empty");
        assertTrue(dateLends.stream().anyMatch(l -> l.getReturnDate().equals(TEST_RETURN_DATE)),
                "All lends should have the test return date");
    }

    @Test
    @Order(8)
    void testGetLendsByAll() {
        List<Lends> allFilteredLends = lendsDAO.getLendsByAll(
                TEST_BOOK_ID, TEST_CUSTOMER_ID, TEST_RETURN_DATE);

        
        assertFalse(allFilteredLends.isEmpty(), "Filtered lends list should not be empty");
        assertTrue(allFilteredLends.stream().anyMatch(l ->
                        l.getBookId() == TEST_BOOK_ID &&
                                l.getCustomerId() == TEST_CUSTOMER_ID &&
                                l.getReturnDate().equals(TEST_RETURN_DATE)),
                "All lends should match all test criteria");
    }

    @Test
    @Order(9)
    void testDeleteLend() {
        lendsDAO.deleteLend(testLend);
        Lends deletedLend = lendsDAO.readLend(testLend.getId());
        
        assertNull(deletedLend, "Lend should be deleted");
    }

    @Test
    void testGetLendsByBookIdCustomerIdReturned() {
        Lends newLend = new Lends(TEST_BOOK_ID, TEST_CUSTOMER_ID, TEST_RETURN_DATE, true);
        lendsDAO.createLend(newLend);

        List<Lends> filteredLends = lendsDAO.getLendsByBookIdCustomerIdReturned(
                TEST_BOOK_ID, TEST_CUSTOMER_ID, true);

        
        assertFalse(filteredLends.isEmpty(), "Filtered lends list should not be empty");
        assertTrue(filteredLends.stream().anyMatch(l ->
                        l.getBookId() == TEST_BOOK_ID &&
                                l.getCustomerId() == TEST_CUSTOMER_ID &&
                                l.isReturned()),
                "All lends should match all test criteria");

        
        lendsDAO.deleteLend(newLend);
    }

    @Test
    void testGetLendsByBookIdReturnDateReturned() {
        Lends newLend = new Lends(TEST_BOOK_ID, TEST_CUSTOMER_ID, TEST_RETURN_DATE, true);
        lendsDAO.createLend(newLend);

        List<Lends> filteredLends = lendsDAO.getLendsByBookIdReturnDateReturned(
                TEST_BOOK_ID, TEST_RETURN_DATE, true);


        assertFalse(filteredLends.isEmpty(), "Filtered lends list should not be empty");
        assertTrue(filteredLends.stream().anyMatch(l ->
                        l.getBookId() == TEST_BOOK_ID &&
                                l.getReturnDate().equals(TEST_RETURN_DATE) &&
                                l.isReturned()),
                "All lends should match all test criteria");

        
        lendsDAO.deleteLend(newLend);
    }

    @Test
    void testGetLendsByCustomerIdReturnDateReturned() {
        Lends newLend = new Lends(TEST_BOOK_ID, TEST_CUSTOMER_ID, TEST_RETURN_DATE, true);
        lendsDAO.createLend(newLend);

        List<Lends> filteredLends = lendsDAO.getLendsByCustomerIdReturnDateReturned(
                TEST_CUSTOMER_ID, TEST_RETURN_DATE, true);

        assertFalse(filteredLends.isEmpty(), "Filtered lends list should not be empty");
        assertTrue(filteredLends.stream().anyMatch(l ->
                        l.getCustomerId() == TEST_CUSTOMER_ID &&
                                l.getReturnDate().equals(TEST_RETURN_DATE) &&
                                l.isReturned()),
                "All lends should match all test criteria");

        lendsDAO.deleteLend(newLend);
    }

    @Test
    void testGetLateLendsNotification(){
        Lends lateLend = new Lends(TEST_BOOK_ID, TEST_CUSTOMER_ID,
                LocalDate.now().minusDays(10), false);
        lendsDAO.createLend(lateLend);

        List<VirtualLend> lateLends = lendsDAO.getLateLendsNotification();

        assertFalse(lateLends.isEmpty(), "Late lends list should not be empty");
        assertTrue(lateLends.stream().anyMatch(l ->
                        Objects.equals(l.getCustomerName(), "Test Customer") && Objects.equals(l.getBookTitle(), "Test Book")),
                "Should find at least one late and unreturned lend");


        lendsDAO.deleteLend(lateLend);
    }
}