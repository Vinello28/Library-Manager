package prj.library.networking.messages;

public class DeleteMessage extends Message {

    public DeleteMessage() {
        this.operation = Operation.REMOVE_BOOK;
    }

    public DeleteMessage(Object message) {
        this.message = (int) message;
        this.operation = Operation.REMOVE_BOOK;
    }
}
