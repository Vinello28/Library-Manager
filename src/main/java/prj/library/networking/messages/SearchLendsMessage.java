package prj.library.networking.messages;

import prj.library.models.Lends;

public class SearchLendsMessage extends Message {

    public SearchLendsMessage() {
    }

    public SearchLendsMessage(Operation op, Object message) {
        this.operation = op;
        this.message = (Lends) message;
    }


    public Lends getLend() {
        return (Lends) message;
    }
}
