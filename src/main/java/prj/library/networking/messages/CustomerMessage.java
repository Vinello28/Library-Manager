package prj.library.networking.messages;

import prj.library.models.Customer;

public class CustomerMessage extends Message {

    public CustomerMessage(Operation operation, Customer message) {
        this.operation = operation;
        this.message = message;
    }

    public Customer getCustomer() {
        return (Customer) message;
    }
}
