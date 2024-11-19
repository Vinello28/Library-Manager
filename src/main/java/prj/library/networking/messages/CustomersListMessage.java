package prj.library.networking.messages;

import prj.library.models.Customer;
import java.util.ArrayList;
import java.util.List;

/**
 * Message to send a list of customers
 */
public class CustomersListMessage extends Message {

    public CustomersListMessage(Operation operation, ArrayList<Customer> message) {
        super(operation, message);
    }

    public List<Customer> getCustomers() {
        return (List<Customer>) message;
    }
}
