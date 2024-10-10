package prj.library.networking.messages;

public class GenericMessage implements Message {

    private Operation operation;
    private Boolean message;


    public GenericMessage() {
        this.operation = Operation.GENERIC_RESPONSE;
    }

    public GenericMessage(Object message) {
        this.operation = Operation.GENERIC_RESPONSE;
        this.message = (Boolean) message;
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
        this.message = (Boolean) message;
    }
}
