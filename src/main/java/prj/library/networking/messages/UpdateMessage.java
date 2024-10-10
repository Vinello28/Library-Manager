package prj.library.networking.messages;

import prj.library.models.Book;

public class UpdateMessage implements Message {

    private Book message;
    private Operation operation;


    public UpdateMessage() {
        this.operation = Operation.UPDATE_BOOK;
    }

    public UpdateMessage(Object message) {
        this.message = (Book) message;
        this.operation = Operation.UPDATE_BOOK;
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
