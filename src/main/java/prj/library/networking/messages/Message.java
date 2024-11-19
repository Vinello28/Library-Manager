package prj.library.networking.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

    protected Operation operation;
    protected Object message;

    /**
     * Constructor.
     * @param operation operation of the message
     * @param message message of the message
     */
    public Message(Operation operation, Object message){
        this.operation = operation;
        this.message = message;
    }

    /**
     * @return operation of the message
     */
    public Operation getOperation(){
        return operation;
    }

    /**
     * @return message of the message
     */
    public Object getMessage(){
        return message;
    }

    /**
     * @param op operation to set
     */
    public void setOperation(Operation op){
        this.operation = op;
    }

    /**
     * @param message message to set
     */
    public void setMessage(Object message){
        this.message = message;
    }

}
