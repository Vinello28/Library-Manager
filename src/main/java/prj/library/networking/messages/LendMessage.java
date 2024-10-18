package prj.library.networking.messages;

import prj.library.models.Lends;

public class LendMessage extends Message {

    public LendMessage(Operation operation, Object lend) {
        this.operation = operation;
        message = (Lends) lend;
    }

    public Lends getLend() {
        return (Lends) message;
    }
}
