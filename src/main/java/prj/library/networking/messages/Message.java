package prj.library.networking.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

    protected Operation operation;
    protected Object message;

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
