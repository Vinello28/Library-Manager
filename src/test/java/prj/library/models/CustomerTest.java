package prj.library.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Customer class.
 * 
 * The CustomerTest class tests the Customer class.
 * It tests the following methods:
 * - Testing getter methods: getId, getName, getEmail, getPhone, getAddress.
 * - Testing setter methods: setId, setName, setEmail, setPhone, setAddress.
 * 
 * The setUp method initializes a Customer object with predefined values before each test method is executed.
 * The test methods use assertions to verify that the expected values match the actual values returned by the Customer object's methods.
 */
class CustomerTest {

    Customer customer = new Customer();

    /**
     * Sets up the test environment before each test method is executed.
     * Initializes a Customer object with predefined values.
     */
    @BeforeEach
    void setUp() {
        customer = new Customer(1, "Paolo", "paolo@gmail.com", "09123456789", "Manila");
    }

    @Test
    void getId() {
        assertEquals(1, customer.getId());
    }

    @Test
    void getName() {
        assertEquals("Paolo", customer.getName());
    }

    @Test
    void getEmail() {
        assertEquals("paolo@gmail.com", customer.getEmail());
    }

    @Test
    void getPhone() {
        assertEquals("09123456789", customer.getPhone());
    }

    @Test
    void setId() {
        customer.setId(2);
        assertEquals(2, customer.getId());
    }

    @Test
    void setName() {
        customer.setName("Juan");
        assertEquals("Juan", customer.getName());
    }

    @Test
    void setEmail() {
        customer.setEmail("paolino@gmail.com");
        assertEquals("paolino@gmail.com", customer.getEmail());
    }

    @Test
    void setPhone() {
        customer.setPhone("09123456788");
        assertEquals("09123456788", customer.getPhone());
    }

    @Test
    void getAddress() {
        assertEquals("Manila", customer.getAddress());
    }

    @Test
    void setAddress() {
        customer.setAddress("Ancona");
        assertEquals("Ancona", customer.getAddress());
    }
}