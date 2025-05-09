package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

//A file-based output strategy for saving patient data to text files.
//
//<p>This class writes incoming health data to a separate file per label (e.g., "ECG", "Alert").
//Each label is mapped to a filename in the specified base directory.

//class name should be upper case
public class FileOutputStrategy implements OutputStrategy {
        //The base directory where output files are stored.

        //field name should be lower case
    private String baseDirectory;
        //Maps each label to its corresponding output file path.

        //removed final and changed name
    private ConcurrentHashMap<String, String> filemap = new ConcurrentHashMap<>();

    //Constructs a new FileOutputStrategy with the given base directory.
    //
    //@param baseDirectory The root directory where all output files will be saved.

    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }

    //Writes a formatted line of data to the appropriate file based on the label.
    //Creates the directory and file if they do not already exist.
    //param patientId The ID of the patient whose data is being written.
    //@param timestamp The time at which the data was generated (in milliseconds since epoch).
    //@param label     The type of data (e.g., "Alert", "HeartRate") used to name the file.
    //@param data      The actual data value to be written as a string.

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            //Ensure the output directory exists
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Retrieve or create the file path for this label
        //lowerCamelCase
        String filePath = filemap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file in append mode
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
                     out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}