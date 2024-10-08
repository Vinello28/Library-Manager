package prj.library.networking;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 60129;

    private ExecutorService pool = Executors.newFixedThreadPool(10);

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}