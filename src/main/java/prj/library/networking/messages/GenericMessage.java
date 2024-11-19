package prj.library.networking.messages;

/**
 * Generic message class
 */
public class GenericMessage extends Message {

    public GenericMessage(Object message) {
        super(Operation.GENERIC_RESPONSE, message);
    }

    /**
     * @return the response
     */
    public Boolean getResponse() {
        return (Boolean) message;
    }
}
