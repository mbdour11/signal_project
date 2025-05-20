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
        // Arrange
        long now = System.currentTimeMillis();
        PatientRecord record = new PatientRecord(1, 185.0, "BloodPressureSystolic", now);
        testPatient.addRecord(record);

        // Act
        alertGenerator.evaluateData(testPatient);

        // Assert — if you’re only printing, just manually check output
        System.out.println("✔ Test passed if alert printed for systolic 185");
    }
}
