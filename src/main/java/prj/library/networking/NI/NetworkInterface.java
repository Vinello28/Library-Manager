package prj.library.networking.NI;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * NetworkInterface class that is used to send and receive objects over a network connection.
 */
public class NetworkInterface implements NIInterface {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * Constructor for the NetworkInterface class.
     * @param socket the socket to use for the network connection
     */
    public NetworkInterface(Socket socket) {
        try {
            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.out.println("Error creating network interface: " + e.getMessage());
        }
    }

    public void send(Object object) {
        try {
            out.writeObject(object);
            out.flush();
        } catch (Exception e) {
            System.out.println("Error sending object: " + e.getMessage());
        }
    }

    public Object receive() {
        try {
            return in.readObject();
        } catch (Exception e) {
            System.out.println("Error receiving object: " + e.getMessage());
            safeDisconnection();
        }
        return null;
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    /**
     * Safely disconnects the network interface.
     */
    private void safeDisconnection(){
        System.out.println("Network Interface | INFO: Safe disconnection starting...");
        close();
        System.out.println("Network Interface | INFO: socket closed");
        Thread.currentThread().interrupt();
        System.out.println("Network Interface | INFO: interrupting thread");
        if (Thread.currentThread().isInterrupted()) System.out.println("Network Interface | INFO: Thread interrupted, safe disconnection completed");
    }



}
