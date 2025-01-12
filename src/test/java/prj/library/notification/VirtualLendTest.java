package prj.library.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the VirtualLend class.
 * 
 * Test class for the VirtualLend class, ensuring that its getter and setter
 * methods behave as expected. The tests verify the correct handling of book title,
 * customer email, customer name, and return date.
 */
class VirtualLendTest {
    private VirtualLend virtualLend;

    /**
     * Initializes the VirtualLend object before each test, providing a sample
     * book title, user email, and user name for testing purposes.
     */
    @BeforeEach
    void setUp() {
        virtualLend = new VirtualLend("Test Book", "Test Email", "Test Name", null);
    }

    @Test
    void getBookTitle() {
        assertEquals("Test Book", virtualLend.getBookTitle());
    }

    @Test
    void getCustomerEmail() {
        assertEquals("Test Email", virtualLend.getCustomerEmail());
    }

    @Test
    void getCustomerName() {
        assertEquals("Test Name", virtualLend.getCustomerName());
    }

    @Test
    void getReturnDate() {
        assertNull(virtualLend.getReturnDate());
    }

    @Test
    void setBookTitle() {
        virtualLend.setBookTitle("New Book");
        assertEquals("New Book", virtualLend.getBookTitle());
    }

    @Test
    void setCustomerEmail() {
        virtualLend.setCustomerEmail("New Email");
        assertEquals("New Email", virtualLend.getCustomerEmail());
    }

    @Test
    void setCustomerName() {
        virtualLend.setCustomerName("New Name");
        assertEquals("New Name", virtualLend.getCustomerName());
    }

    @Test
    void setReturnDate() {
        virtualLend.setReturnDate(null);
        assertNull(virtualLend.getReturnDate());
    }
}