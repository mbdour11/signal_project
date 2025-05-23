package com.cardio_generator;

import java.util.Random;

/**
 * Simulates health data readings for patients in a monitoring system.
 * <p>
 * Implements the Singleton Pattern to ensure only one simulator instance exists globally.
 */
public class HealthDataSimulator {
    private static HealthDataSimulator instance; // Singleton instance
    private Random random;

    /**
     * Private constructor prevents instantiation from outside the class.
     * Initializes the random number generator for simulation.
     */
    private HealthDataSimulator() {
        this.random = new Random();
    }

    /**
     * Returns the singleton instance of the HealthDataSimulator class.
     * If the instance does not exist, it is created.
     *
     * @return the singleton instance of HealthDataSimulator
     */
    public static HealthDataSimulator getInstance() {
        if (instance == null) {
            instance = new HealthDataSimulator();
        }
        return instance;
    }

    /**
     * Simulates a heart rate value between 60 and 100 bpm.
     *
     * @return simulated heart rate
     */
    public int generateHeartRate() {
        return 60 + random.nextInt(41);
    }

    /**
     * Simulates a systolic blood pressure value between 90 and 140 mmHg.
     *
     * @return simulated systolic blood pressure
     */
    public int generateSystolicBloodPressure() {
        return 90 + random.nextInt(51);
    }

    /**
     * Simulates a diastolic blood pressure value between 60 and 90 mmHg.
     *
     * @return simulated diastolic blood pressure
     */
    public int generateDiastolicBloodPressure() {
        return 60 + random.nextInt(31);
    }

    /**
     * Simulates a blood oxygen saturation (SpO2) percentage between 90 and 100%.
     *
     * @return simulated SpO2 level
     */
    public int generateSpO2() {
        return 90 + random.nextInt(11);
    }

    /**
     * Simulates an ECG measurement as a floating-point value.
     *
     * @return simulated ECG value
     */
    public double generateECG() {
        return random.nextDouble() * 2.0; // example value range: 0.0 - 2.0
    }
}
