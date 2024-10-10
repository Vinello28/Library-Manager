package prj.library.networking.messages;

import prj.library.models.Book;
import java.util.ArrayList;

public class RefreshMessage implements Message {

    private ArrayList<Book> message;
    private Operation operation;

    public RefreshMessage() {
        this.operation = Operation.GET_BOOKS;
    }

    public RefreshMessage(ArrayList<Book> message) {
        this.message = message;
        this.operation = Operation.GET_BOOKS;
    }

    @Override
    public Operation getOperation() {
        return operation;
    }

    @Override
    public Object getMessage() {
        return message;
    }

    @Override
    public void setOperation(Operation op) {
        this.operation = op;
    }

    @Override
    public void setMessage(Object message) {
        this.message = (ArrayList<Book>) message;
    }
}
