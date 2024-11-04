package prj.library.networking;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 60129;

    private ExecutorService pool = Executors.newFixedThreadPool(10);

    public void start() {
        System.out.println("SERVER | INFO: Server is starting...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("SERVER | INFO: Server started");
            System.out.println("SERVER | INFO: Server is listening on port " + PORT + " at " + InetAddress.getLocalHost().getHostAddress());

            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.out.println("SERVER | CRITICAL_ERROR: exception message -> " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}