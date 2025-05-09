package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;
//Generates simulated blood saturation (SpO2) data for patients.
//
//<p>This generator creates random but realistic saturation levels (between 90% and 100%)
//and introduces small fluctuations over time. The values are updated and stored per patient.
public class BloodSaturationDataGenerator implements PatientDataGenerator {

    //A shared random number generator used for data simulation.
    private static final Random random = new Random();

    //Stores the last known saturation value for each patient.
    private int[] lastSaturationValues;

    //Constructs a new blood saturation data generator.
    //
    //@param patientCount The number of patients. Must be greater than 0.

    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize each patient with a baseline saturation between 95% and 100%
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }

    //Simulates and outputs updated blood saturation data for a specific patient.
    //
    //<p>The value is randomly adjusted by ±1 and clamped to a range of 90%–100%.
    //@param patientId      The ID of the patient for whom to generate data.
    //@param outputStrategy The output method used to send the generated data.
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Small random change: -1, 0, or +1
            int variation = random.nextInt(3) - 1; // -1, 0, or 1 to simulate small fluctuations
            int newSaturationValue = lastSaturationValues[patientId] + variation;

            // Clamp value between 90 and 100
            newSaturationValue = Math.min(Math.max(newSaturationValue, 90), 100);
            lastSaturationValues[patientId] = newSaturationValue;

            // Output value as a percentage
            outputStrategy.output(patientId, System.currentTimeMillis(), "Saturation",
                    Double.toString(newSaturationValue) + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
