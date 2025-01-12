package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Customer;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CustomersListMessage class.
 * 
 * This test class verifies the functionality of the CustomersListMessage class,
 * which is responsible for handling messages related to a list of customers.
 * 
 * The following test methods are included:
 * 
 * - setUp(): Sets up the test environment before each test method is executed.
 *   Initializes a list of customers with sample data and creates a CustomersListMessage
 *   with the specified operation and customers list.
 * 
 * - getCustomers(): Tests the getCustomers() method to ensure it returns the correct
 *   list of customers.
 */
class CustomersListMessageTest {

    CustomersListMessage customersListMessage;

    /**
     * Sets up the test environment before each test execution.
     * Initializes a list of customers and adds a sample customer to it.
     * Creates a CustomersListMessage with the operation type RESULT_CUSTOMERS and the list of customers.
     */
    @BeforeEach
    void setUp() {
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Paolo", "unclepear@lemele.apple", "1234567890", "via degli aranci"));
        customersListMessage = new CustomersListMessage(Operation.RESULT_CUSTOMERS, customers);
    }

    @Test
    void getCustomers() {
        assertEquals("Paolo", customersListMessage.getCustomers().get(0).getName());
        assertEquals("unclepear@lemele.apple", customersListMessage.getCustomers().get(0).getEmail());
    }
}