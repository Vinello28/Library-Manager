package prj.library.networking;

import prj.library.database.DAO.LendsDAO;
import prj.library.notification.EmailNotificationService;
import prj.library.notification.NotificationScheduler;
import prj.library.utils.CLIUtils;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 60129;
    private ExecutorService pool = Executors.newFixedThreadPool(10);

    private static EmailNotificationService notificationService;

    public Server() {
        notificationService = new EmailNotificationService(new LendsDAO());
        NotificationScheduler scheduler = new NotificationScheduler(notificationService);
        scheduler.startScheduler();
    }

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