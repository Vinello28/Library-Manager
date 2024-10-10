package prj.library.networking.messages;

import java.io.Serializable;

public interface Message extends Serializable {

    /**
     * @return operation of the message
     */
    public Operation getOperation();

    /**
     * @return message of the message
     */
    public Object getMessage();

    /**
     * @param op operation to set
     */
    public void setOperation(Operation op);

    /**
     * @param message message to set
     */
    public void setMessage(Object message);

}
