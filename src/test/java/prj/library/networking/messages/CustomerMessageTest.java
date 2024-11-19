package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Customer;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMessageTest {

    CustomerMessage customerMessage;

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