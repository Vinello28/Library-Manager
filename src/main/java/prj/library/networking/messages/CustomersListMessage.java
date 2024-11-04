package prj.library.networking.messages;

import prj.library.models.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomersListMessage extends Message {

    public CustomersListMessage(Operation operation, ArrayList<Customer> message) {
        this.operation = operation;
        this.message = message;
    }

    public List<Customer> getCustomers() {
        return (List<Customer>) message;
    }
}
