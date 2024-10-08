package prj.library.networking;

import prj.library.models.Book;

import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 60129;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void start() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void sendBook(String operation, Book book) throws IOException, ClassNotFoundException {
        out.writeObject(operation);
        out.writeObject(book);
        System.out.println("Server response: " + in.readObject());
    }

    public Book readBook(int id) throws IOException, ClassNotFoundException {
        out.writeObject("READ");
        out.writeObject(new Book(id, "", "", 0));
        return (Book) in.readObject();
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.start();

            // Example usage
            Book newBook = new Book(0, "The Great Gatsby", "F. Scott Fitzgerald", 1925);
            client.sendBook("CREATE", newBook);

            Book readBook = client.readBook(1);
            System.out.println("Read book: " + readBook);

            readBook.setYear(1926);
            client.sendBook("UPDATE", readBook);

            client.sendBook("DELETE", new Book(1, "", "", 0));

            client.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client exception: " + e.getMessage());
        }
    }
}