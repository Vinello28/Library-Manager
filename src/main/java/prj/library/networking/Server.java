package prj.library.networking;

import prj.library.utils.CLIUtils;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 60129;

    private ExecutorService pool = Executors.newFixedThreadPool(10);

    public void start() {

        CLIUtils.serverStart();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            CLIUtils.serverStarted();
            CLIUtils.serverListening(PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            CLIUtils.serverCriticalError(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}