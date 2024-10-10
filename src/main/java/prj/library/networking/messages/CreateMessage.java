package prj.library.networking.messages;

import prj.library.models.Book;

public class CreateMessage implements Message {

    private Book message;
    private Operation operation;

    public CreateMessage() {
        this.operation = Operation.ADD_BOOK;
    }

    public CreateMessage(Object message) {
        this.message = (Book) message;
        this.operation = Operation.ADD_BOOK;
    }

    @Override
    public Operation getOperation() {
        return this.operation;
    }

    @Override
    public Object getMessage() {
        return this.message;
    }

    @Override
    public void setOperation(Operation op) {
        this.operation = op;
    }

    @Override
    public void setMessage(Object message) {
        this.message = (Book) message;
    }
}
