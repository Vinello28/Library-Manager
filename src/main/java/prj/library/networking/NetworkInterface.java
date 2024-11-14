package prj.library.networking;

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
            close();
            Thread.currentThread().interrupt();
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

}
