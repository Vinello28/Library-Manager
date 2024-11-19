package prj.library.networking.messages;

import prj.library.models.Lends;

/**
 * Message for lending operations
 */
public class LendMessage extends Message {

    public LendMessage(Operation operation, Object lend) {
        super(operation, lend);
    }

    /**
     * @return the lend
     */
    public Lends getLend() {
        return (Lends) message;
    }
}
