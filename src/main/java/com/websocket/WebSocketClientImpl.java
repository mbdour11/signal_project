package com.websocket;

import com.data_management.DataStorage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketClientImpl extends WebSocketClient {

    private DataStorage storage;

    public WebSocketClientImpl(String serverUri, DataStorage storage) {
        super(URI.create(serverUri));
        this.storage = storage;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Connected to WebSocket server.");
    }

    @Override
    public void onMessage(String message) {
        try {
            // Assume JSON format like: {"patientId": 1, "value": 150.0, "type": "BloodPressureSystolic", "timestamp": 123456789}
            String[] parts = message.replace("{", "").replace("}", "").replace("\"", "").split(",");
            int patientId = Integer.parseInt(parts[0].split(":")[1].trim());
            double value = Double.parseDouble(parts[1].split(":")[1].trim());
            String type = parts[2].split(":")[1].trim();
            long timestamp = Long.parseLong(parts[3].split(":")[1].trim());

            storage.addPatientData(patientId, value, type, timestamp);

            System.out.println("Added record for patient " + patientId + ": " + type + " = " + value);

        } catch (Exception e) {
            System.err.println("Failed to parse message: " + message);
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WebSocket closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket error: ");
        ex.printStackTrace();
    }
}
