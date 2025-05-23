package com.data_management;

import com.websocket.WebSocketClientImpl;
import com.data_management.DataStorage;


public class WebSocketDataReader implements DataReader {

    private WebSocketClientImpl client;
    @Override
    public void connectToStream(String uri, DataStorage storage){
        this.client = new WebSocketClientImpl(uri,storage);
            client.connect();

    }
    @Override
    public void readData(DataStorage dataStorage){}
}
