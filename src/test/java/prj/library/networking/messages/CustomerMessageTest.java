package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Customer;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CustomerMessage class.
 * 
 * This test class verifies the functionality of the CustomerMessage class,
 * which is responsible for handling messages related to a single customer.
 * 
 * The following test methods are included:
 * 
 * - setUp(): Sets up the test environment before each test method is executed.
 *   Initializes a Customer object with predefined values and assigns it to a CustomerMessage
 *   with the operation type set to ADD_CUSTOMER.
 * 
 * - getCustomer(): Tests the getCustomer() method to ensure it returns the correct
 *   customer object.
 */
class CustomerMessageTest {

    CustomerMessage customerMessage;

    /**
     * Sets up the test environment before each test execution.
     * Initializes a Customer object with predefined values and assigns it to a CustomerMessage
     * with the operation type set to ADD_CUSTOMER.
     */
    @BeforeEach
    void setUp() {
        Customer customer = new Customer(20, "Giampaolo", "ciao@mail.pasta", "1234567890", "via delle paste");
        customerMessage = new CustomerMessage(Operation.ADD_CUSTOMER, customer);
    }

    @Test
    void getCustomer() {
        Customer customer = customerMessage.getCustomer();
        assertEquals(20, customer.getId());
        assertEquals("Giampaolo", customer.getName());
        assertEquals("via delle paste", customer.getAddress());
    }
}