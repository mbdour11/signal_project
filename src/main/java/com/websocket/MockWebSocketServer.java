package com.websocket;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;

public class MockWebSocketServer extends WebSocketServer {

    public MockWebSocketServer(int port) {
        super(new InetSocketAddress(port));
        System.out.println(" Server constructed on port " + port);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("Client connected: " + conn.getRemoteSocketAddress());

        // Send a fake JSON message every 5 seconds
        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                String msg = "{\"patientId\": 1, \"value\": 185.0, \"type\": \"BloodPressureSystolic\", \"timestamp\": " + System.currentTimeMillis() + "}";
                conn.send(msg);
                System.out.println("Sent message: " + msg);
            }
        }, 1000, 5000);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Client disconnected");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received from client: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println(" onStart() called — server really running!");
    }

    public static void main(String[] args) {
        int port = 8765; // or 8080
        MockWebSocketServer server = new MockWebSocketServer(port);

        try {
            server.start();
            System.out.println(" .start() issued — waiting to bind...");

            Thread.sleep(2000);

            try (Socket s = new Socket("localhost", port)) {
                System.out.println(" Port check successful — server listening.");
            } catch (Exception e) {
                System.err.println(" Port check failed — server not listening.");
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("Server start failed:");
            e.printStackTrace();
        }
    }

}