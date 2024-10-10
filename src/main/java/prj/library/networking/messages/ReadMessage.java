package prj.library.networking.messages;

public class ReadMessage implements Message {

    private int message;
    private Operation operation;

    public ReadMessage() {
        this.operation = Operation.GET_BOOK;
    }

    public ReadMessage(Object message) {
        this.message = (int) message;
        this.operation = Operation.GET_BOOK;
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
