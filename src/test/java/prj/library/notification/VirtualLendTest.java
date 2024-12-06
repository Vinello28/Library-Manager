package prj.library.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VirtualLendTest {
    private VirtualLend virtualLend;

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