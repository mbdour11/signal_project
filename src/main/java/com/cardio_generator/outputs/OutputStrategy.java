package com.cardio_generator.outputs;

//Defines the strategy for handling the output of generated patient data.
//
//<p>Implementing classes determine how the data is delivered, such as writing to a file,
//displaying on the console, or sending through a network connection.

public interface OutputStrategy {

    //Sends output data for a specific patient using the chosen output method.
    void output(int patientId, long timestamp, String label, String data);
}
