package prj.library.networking.messages;

/**
 * Generic message class
 */
public class GenericMessage extends Message {

    public GenericMessage(Operation op, Object message) {
        super(op, message);
    }

    /**
     * @return the response
     */
    public Boolean getResponseBoolean() {
        return (Boolean) message;
    }

    /**
     * @return the response
     */
    public int getResponseInt() {
        return (int) message;
    }
}
