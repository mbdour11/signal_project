package com.websocket;

import com.data_management.DataStorage;
import com.data_management.WebSocketDataReader;

public class WebSocketTestMain {
    public static void main(String[] args) throws InterruptedException {

        DataStorage storage = DataStorage.getInstance();


        String wsUri = "ws://localhost:8765";



        WebSocketDataReader reader = new WebSocketDataReader();
        reader.connectToStream(wsUri, storage);


        Thread.sleep(30000);
    }
}
