package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
//An output strategy that sends patient data over a TCP socket.
//
//<p>This class sets up a TCP server that waits for a single client to connect.
//Once connected, data is streamed to the client in CSV format.
public class TcpOutputStrategy implements OutputStrategy {

    //The server socket that listens for incoming client connections.
    private ServerSocket serverSocket;

    //The socket representing the connected client.
    private Socket clientSocket;

    //The writer used to send data to the client.
    private PrintWriter out;

    //Initializes the TCP server and waits for a client to connect on the given port.
    //Accepting the client happens asynchronously to avoid blocking the main thread.
    //
    //@param port The port number on which the TCP server listens.

    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept client connection asynchronously
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace(); // Log any I/O errors during client connection
                }
            });
        } catch (IOException e) {
            e.printStackTrace(); // Log server startup failure
        }
    }

    //Sends formatted patient data over the connected TCP client socket.
    //If no client is connected yet, the message will not be sent.
    //
    //@param patientId The ID of the patient.
    //@param timestamp The timestamp of the data in milliseconds since epoch.
    //@param label     A short label indicating the data type (e.g., "Alert", "ECG").
    //@param data      The actual data value as a string.

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
