package prj.library.networking.messages;

import prj.library.models.Lends;

import java.util.ArrayList;

public class LendsListMessage extends Message {

    public LendsListMessage(Operation operation, Object message) {
        this.operation = Operation.REFRESH_LENDS;
    }

    public LendsListMessage(Operation operation, ArrayList<Lends> message) {
        this.operation = operation;
        this.message = message;
    }

    /**
     * @return the lends
     */
    public ArrayList<Lends> getLends() {
        return (ArrayList<Lends>) message;
    }

    /**
     * @param lends the lends to set
     */
    public void setLends(ArrayList<Lends> lends) {
        this.message = lends;
    }
}
