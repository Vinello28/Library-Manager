package prj.library.networking;

import prj.library.utils.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import static prj.library.utils.ConfigLoader.getProperty;

public class Server {
    private final int port; //port to listen on
    private ExecutorService pool = Executors.newFixedThreadPool(10); //thread pool

    public Server() {
        port = Integer.parseInt(getProperty("server.port"));
    }

    /**
     * Starts the server
     */
    public void start() {
        CLIUtils.serverStart();

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            CLIUtils.serverStarted();
            CLIUtils.serverListening(port);

            while (true) {
                Socket clientSocket = serverSocket.accept(); //accept new connection
                pool.execute(new ClientHandler(clientSocket)); //handle client in a new client handler
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