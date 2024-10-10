package prj.library.networking.messages;

public class DeleteMessage implements Message {

    private int message;
    private Operation operation;

    public DeleteMessage() {
        this.operation = Operation.REMOVE_BOOK;
    }

    public DeleteMessage(Object message) {
        this.message = (int) message;
        this.operation = Operation.REMOVE_BOOK;
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
        this.message = (int) message;
    }
}
