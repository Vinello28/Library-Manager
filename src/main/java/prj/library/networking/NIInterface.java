package prj.library.networking;

public interface NIInterface {

    /**
     * Sends an object over the network connection.
     *
     * @param object the object to send
     */
    void send(Object object);

    /**
     * Receives an object from the network connection.
     *
     * @return the object received
     */
    public Object receive();

    /**
     * Closes the network connection.
     */
    public void close();
}
