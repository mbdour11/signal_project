package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

//Interface for generating simulated patient health data.
//
//<p>Implementing classes must define how data is generated for a specific patient.
//The generated data is passed to an output strategy (e.g., console, file, network).

public interface PatientDataGenerator {

    //Generates data for a single patient and sends it to the given output strategy.
    //
    //@param patientId        The ID of the patient for whom to generate data. Must be a positive integer.
    //@param outputStrategy   The output method used to send the generated data (e.g., to a file or console).
    void generate(int patientId, OutputStrategy outputStrategy);
}
