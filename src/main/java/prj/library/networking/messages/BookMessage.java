package prj.library.networking.messages;

import prj.library.models.Book;

public class BookMessage implements Message {

    private Operation operation;
    private Book message;

    public BookMessage() {
        this.operation = Operation.RESP_BOOK;
    }

    public BookMessage(Book book) {
        this.operation = Operation.RESP_BOOK;
        this.message = book;
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
        this.message = (Book) message;
    }
}
