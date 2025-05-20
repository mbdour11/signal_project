package com.data_management;

import java.io.*;
import java.nio.file.*;

public class CholestrolFileReader implements DataReader {

    private final String directoryPath;

    public CholestrolFileReader(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void readData(DataStorage storage) {
        try {
            Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().toLowerCase().contains("cholesterol"))
                    .forEach(filePath -> {
                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                PatientRecord record = parseLine(line);
                                if (record != null) {
                                    storage.addPatientData(
                                            record.getPatientId(),
                                            record.getMeasurementValue(),
                                            record.getRecordType(),
                                            record.getTimestamp()
                                    );


                                }
                            }
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + filePath);
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            System.err.println("Failed to walk directory: " + directoryPath);
            e.printStackTrace();
        }
    }

    private PatientRecord parseLine(String line) {
        try {
            String[] parts = line.split(",");

            int patientId = Integer.parseInt(parts[0].split(":")[1].trim());
            long timestamp = Long.parseLong(parts[1].split(":")[1].trim());
            String label = parts[2].split(":")[1].trim();
            double value = Double.parseDouble(parts[3].split(":")[1].trim());

            return new PatientRecord(patientId, value, label, timestamp );
        } catch (Exception e) {
            System.err.println("Failed to parse line: " + line);
            return null;
        }
    }
}
