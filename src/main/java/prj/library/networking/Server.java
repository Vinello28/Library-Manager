package prj.library.networking;

import prj.library.database.DAO.LendsDAO;
import prj.library.notification.EmailNotificationService;
import prj.library.notification.NotificationScheduler;
import prj.library.utils.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import static prj.library.utils.ConfigLoader.getProperty;

public class Server {
    private final int port; //port to listen on
    private ExecutorService pool = Executors.newFixedThreadPool(10); //thread pool
    private static EmailNotificationService notificationService;

    public Server() {
        port = Integer.parseInt(getProperty("server.port"));
        notificationService = new EmailNotificationService(new LendsDAO());
        NotificationScheduler scheduler = new NotificationScheduler(notificationService);
        scheduler.startScheduler();
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

    /**
     * Send notifications to users
     */
    public static void sendNotification(){
        notificationService.checkAndSendNotifications();
    }


    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}