package prj.library.networking.messages;

public class GenericMessage extends Message {

    public GenericMessage() {
        this.operation = Operation.GENERIC_RESPONSE;
    }

    public GenericMessage(Object message) {
        this.operation = Operation.GENERIC_RESPONSE;
        this.message = (Boolean) message;
    }

    public Boolean getResponse() {
        return (Boolean) message;
    }
}
