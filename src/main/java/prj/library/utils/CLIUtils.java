package prj.library.utils;

import prj.library.networking.messages.Message;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CLIUtils {
    public static void serverStart() {
        System.out.println("SERVER | INFO: Server is starting...");
    }

    public static void serverStarted() {
        System.out.println("SERVER | INFO: Server started");
    }

    public static void serverListening(int port) {
        try {
            System.out.println("SERVER | INFO: Server is listening on port " + port + " at " + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("SERVER | CRITICAL_ERROR: exception message -> " + e.getMessage());
        }
    }

    public static void serverCriticalError(String message) {
        System.out.println("SERVER | CRITICAL_ERROR: exception message -> " + message);
    }

    public static void clientConnected() {
        System.out.println("CLIENT | INFO: Client connected");
    }

    public static void clientDisconnected() {
        System.out.println("CLIENT | INFO: Client disconnected");
    }

    public static void clientCriticalError(String message) {
        System.out.println("CLIENT | CRITICAL_ERROR: exception message -> " + message);
    }

    public static void clientRequest(String message) {
        System.out.println("CLIENT | INFO: Client request -> " + message);
    }

    public static void clientResponse(String message) {
        System.out.println("CLIENT | INFO: Server response -> " + message);
    }

    public static void clientInfo(String message) {
        System.out.println("CLIENT | INFO: " + message);
    }

    public static void clientError(Message message) {
        System.out.println("CLIENT | ERROR: " + message.getMessage());
    }

    public static void clientDebug(String message) {
        System.out.println("CLIENT | DEBUG_INFO: " + message);
    }

    public static void serverDebug(String message) {
        System.out.println("SERVER | DEBUG_INFO: " + message);
    }


}
