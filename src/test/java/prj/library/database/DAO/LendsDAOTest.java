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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LendsDAOTest {
    private static LendsDAO lendsDAO;
    private static Lends testLend;
    private static Book book;
    private static Customer customer;

    private static int TEST_BOOK_ID;
    private static int TEST_CUSTOMER_ID;
    private static final LocalDate TEST_RETURN_DATE = LocalDate.now().plusDays(7);
    private static final boolean TEST_RETURNED = false;


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
        //Act
        lendsDAO.createLend(testLend);
        Lends l = lendsDAO.getLendsByAll(TEST_BOOK_ID, TEST_CUSTOMER_ID, TEST_RETURN_DATE).get(0);
        //Assert
        assertNotEquals(0, l.getId(), "Lend ID should be set after creation");
    }

    @Test
    @Order(2)
    void testReadLend() {
        Lends l = lendsDAO.getLendsByAll(TEST_BOOK_ID, TEST_CUSTOMER_ID, TEST_RETURN_DATE).get(0);
        //Act
        Lends retrievedLend = lendsDAO.readLend(l.getId());

        //Assert
        assertNotNull(retrievedLend, "Retrieved lend should not be null");
        assertEquals(testLend.getBookId(), retrievedLend.getBookId());
        assertEquals(testLend.getCustomerId(), retrievedLend.getCustomerId());
        assertEquals(testLend.getReturnDate(), retrievedLend.getReturnDate());
        assertEquals(testLend.isReturned(), retrievedLend.isReturned());
    }

    @Test
    @Order(3)
    void testUpdateLend() {
        //Arrange
        testLend.setReturned(true);

        //Act
        lendsDAO.updateLend(testLend);
        Lends l = lendsDAO.getLendsByAll(TEST_BOOK_ID, TEST_CUSTOMER_ID, TEST_RETURN_DATE).get(0);
        Lends updatedLend = lendsDAO.readLend(l.getId());

        //Assert
        assertNotNull(updatedLend, "Updated lend should not be null");
        assertTrue(updatedLend.isReturned(), "Lend should be marked as returned");
    }

    @Test
    @Order(4)
    void testGetLendsByCustomerId() {
        //Act
        List<Lends> customerLends = lendsDAO.getLendsByCustomerId(TEST_CUSTOMER_ID);

        //Assert
        assertFalse(customerLends.isEmpty(), "Customer lends list should not be empty");
        assertTrue(customerLends.stream().anyMatch(l -> l.getCustomerId() == TEST_CUSTOMER_ID && l.getBookId() == TEST_BOOK_ID && l.getReturnDate().equals(TEST_RETURN_DATE)),
                "All lends should belong to test customer");
    }

    @Test
    @Order(5)
    void testGetLendsByBookId() {
        //Act
        List<Lends> bookLends = lendsDAO.getLendsByBookId(TEST_BOOK_ID);

        //Assert
        assertFalse(bookLends.isEmpty(), "Book lends list should not be empty");
        assertTrue(bookLends.stream().anyMatch(l -> l.getCustomerId() == TEST_CUSTOMER_ID && l.getBookId() == TEST_BOOK_ID && l.getReturnDate().equals(TEST_RETURN_DATE)),
                "All lends should be for test book");
    }

    @Test
    @Order(6)
    void testGetLateLends() {
        //Arrange
        Lends lateLend = new Lends(TEST_BOOK_ID, TEST_CUSTOMER_ID,
                LocalDate.now().minusDays(1), false);
        lendsDAO.createLend(lateLend);

        //Act
        List<Lends> lateLends = lendsDAO.getLateLends();

        //Assert
        assertFalse(lateLends.isEmpty(), "Late lends list should not be empty");
        assertTrue(lateLends.stream().anyMatch(l ->
                        l.getCustomerId() == TEST_CUSTOMER_ID && l.getBookId() == TEST_BOOK_ID && l.getReturnDate().equals(LocalDate.now().minusDays(1))),
                "Should find at least one late and unreturned lend");

        //Cleanup
        lendsDAO.deleteLend(lateLend);
    }

    @Test
    @Order(7)
    void testGetLendsByReturnDate() {
        //Act
        List<Lends> dateLends = lendsDAO.getLendsByReturnDate(TEST_RETURN_DATE);

        //Assert
        assertFalse(dateLends.isEmpty(), "Date lends list should not be empty");
        assertTrue(dateLends.stream().anyMatch(l -> l.getReturnDate().equals(TEST_RETURN_DATE)),
                "All lends should have the test return date");
    }

    @Test
    @Order(8)
    void testGetLendsByAll() {
        //Act
        List<Lends> allFilteredLends = lendsDAO.getLendsByAll(
                TEST_BOOK_ID, TEST_CUSTOMER_ID, TEST_RETURN_DATE);

        //Assert
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
        //Act
        lendsDAO.deleteLend(testLend);
        Lends deletedLend = lendsDAO.readLend(testLend.getId());

        //Assert
        assertNull(deletedLend, "Lend should be deleted");
    }

    @Test
    void testGetLendsByBookIdCustomerIdReturned() {
        //Arrange
        Lends newLend = new Lends(TEST_BOOK_ID, TEST_CUSTOMER_ID, TEST_RETURN_DATE, true);
        lendsDAO.createLend(newLend);

        //Act
        List<Lends> filteredLends = lendsDAO.getLendsByBookIdCustomerIdReturned(
                TEST_BOOK_ID, TEST_CUSTOMER_ID, true);

        //Assert
        assertFalse(filteredLends.isEmpty(), "Filtered lends list should not be empty");
        assertTrue(filteredLends.stream().anyMatch(l ->
                        l.getBookId() == TEST_BOOK_ID &&
                                l.getCustomerId() == TEST_CUSTOMER_ID &&
                                l.isReturned()),
                "All lends should match all test criteria");

        //Cleanup
        lendsDAO.deleteLend(newLend);
    }

    @Test
    void testGetLendsByBookIdReturnDateReturned() {
        //Arrange
        Lends newLend = new Lends(TEST_BOOK_ID, TEST_CUSTOMER_ID, TEST_RETURN_DATE, true);
        lendsDAO.createLend(newLend);

        //Act
        List<Lends> filteredLends = lendsDAO.getLendsByBookIdReturnDateReturned(
                TEST_BOOK_ID, TEST_RETURN_DATE, true);

        //Assert
        assertFalse(filteredLends.isEmpty(), "Filtered lends list should not be empty");
        assertTrue(filteredLends.stream().anyMatch(l ->
                        l.getBookId() == TEST_BOOK_ID &&
                                l.getReturnDate().equals(TEST_RETURN_DATE) &&
                                l.isReturned()),
                "All lends should match all test criteria");

        //Cleanup
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