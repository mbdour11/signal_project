package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

//Generates simulated alert data for patients, such as emergency button presses.
//
//<p>This generator models a simple alert system where alerts may be triggered randomly
//and resolved after a short period. It keeps track of each patient's alert state.
public class AlertGenerator implements PatientDataGenerator {

    //Shared random number generator used for alert triggering and resolution.
    //constant names should be in upper case
    public static final Random RANDOM_GENERATOR = new Random();

    //Stores the current alert state for each patient (true = active, false = resolved).
    //field names must be lowerCamelCase
    private boolean[] alertStates; // false = resolved, true = pressed

    //Constructs a new AlertGenerator for a given number of patients.
    //
    //@param patientCount The number of patients to track. Must be greater than 0.
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    //Simulates alert triggering or resolution for a specific patient.
    //
    //<p>If the alert is already active, it has a 90% chance of being resolved.
    //If inactive, a random process decides whether a new alert is triggered.
    //@param patientId      The ID of the patient being simulated.
    //@param outputStrategy The output method used to report the alert status.

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // Resolve alert with 90% probability
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                //local variables must be lowerCamelCase
                double lambda = 0.1; // Average alert rate per time unit
                double probability = -Math.expm1(-lambda); // Poisson process approximation

                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < probability;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
