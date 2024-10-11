package prj.library.networking.messages;

public class ReadMessage extends Message {

    public ReadMessage() {
        this.operation = Operation.GET_BOOK;
    }

    public ReadMessage(Object message) {
        this.message = (int) message;
        this.operation = Operation.GET_BOOK;
    }
}
