package prj.library.networking.NI;

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
    Object receive();

    /**
     * Closes the network connection.
     */
    void close();

}
