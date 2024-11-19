package prj.library.networking.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prj.library.models.Customer;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class CustomersListMessageTest {

    CustomersListMessage customersListMessage;

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