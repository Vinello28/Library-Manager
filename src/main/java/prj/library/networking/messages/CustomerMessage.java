package prj.library.networking.messages;

import prj.library.models.Customer;

public class CustomerMessage extends Message {

    public CustomerMessage(Operation operation, Customer message) {
        super(operation, message);
    }

    public Customer getCustomer() {
        return (Customer) message;
    }
}
