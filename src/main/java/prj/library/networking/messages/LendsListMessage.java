package prj.library.networking.messages;

import prj.library.models.Lends;

import java.util.ArrayList;

/**
 * Message class for sending a list of Lends objects.
 */
public class LendsListMessage extends Message {

    public LendsListMessage(Operation operation, ArrayList<Lends> message) {
        super(operation, message);
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
