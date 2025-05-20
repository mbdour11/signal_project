package data_management;

import org.junit.jupiter.api.Test;
import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlertGeneratorTest {

    private AlertGenerator alertGenerator;
    private Patient testPatient;

    @BeforeEach
    void setUp() {
        testPatient = new Patient(1);
        DataStorage mockStorage = new DataStorage();
        alertGenerator = new AlertGenerator(mockStorage);
    }

    @Test
    void triggersSystolicHighAlert() {

        long now = System.currentTimeMillis();
        PatientRecord record = new PatientRecord(1, 185.0, "BloodPressureSystolic", now);
        testPatient.addRecord(record);


        alertGenerator.evaluateData(testPatient);


        System.out.println(" Test passed if alert printed for systolic 185");
    }

    @Test
    void triggersDiastolicHighAlert() {
        long now = System.currentTimeMillis();
        PatientRecord record = new PatientRecord(1, 125.0, "BloodPressureDiastolic", now);
        testPatient.addRecord(record);
        alertGenerator.evaluateData(testPatient);
        System.out.println(" Diastolic BP alert test ran.");
    }

    @Test
    void triggersLowSpO2Alert() {
        long now = System.currentTimeMillis();
        PatientRecord record = new PatientRecord(1, 88.0, "SpO2", now);
        testPatient.addRecord(record);
        alertGenerator.evaluateData(testPatient);
        System.out.println(" Low SpO2 alert test ran.");
    }

    @Test
    void triggersRapidSpO2DropAlert() {
        long now = System.currentTimeMillis();
        testPatient.addRecord(new PatientRecord(1, 98.0, "SpO2", now - 5 * 60 * 1000)); // 5 mins ago
        testPatient.addRecord(new PatientRecord(1, 92.0, "SpO2", now - 2 * 60 * 1000)); // 2 mins ago
        testPatient.addRecord(new PatientRecord(1, 91.5, "SpO2", now - 1 * 60 * 1000)); // 1 min ago
        testPatient.addRecord(new PatientRecord(1, 91.0, "SpO2", now));                // now
        alertGenerator.evaluateData(testPatient);
        System.out.println(" Rapid SpO2 drop test ran.");
    }

    @Test
    void triggersHypotensiveHypoxemiaAlert() {
        long now = System.currentTimeMillis();
        testPatient.addRecord(new PatientRecord(1, 85.0, "BloodPressureSystolic", now - 30_000)); // 30 sec ago
        testPatient.addRecord(new PatientRecord(1, 89.0, "SpO2", now)); // now
        alertGenerator.evaluateData(testPatient);
        System.out.println(" Hypotensive Hypoxemia test ran.");
    }

    @Test
    void triggersECGAnomalyAlert() {
        long now = System.currentTimeMillis();
        testPatient.addRecord(new PatientRecord(1, 1.0, "ECG", now - 300_000)); // 5 min ago
        testPatient.addRecord(new PatientRecord(1, 1.2, "ECG", now - 240_000)); // 4 min ago
        testPatient.addRecord(new PatientRecord(1, 1.3, "ECG", now - 180_000)); // 3 min ago
        testPatient.addRecord(new PatientRecord(1, 4.0, "ECG", now));           // current high spike
        alertGenerator.evaluateData(testPatient);
        System.out.println(" ECG anomaly test ran.");
    }
}
