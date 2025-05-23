package alerts;
import com.alerts.OxygenSaturationStrategy;
import com.alerts.AlertStrategy;
import com.alerts.BloodPressureStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.data_management.PatientRecord;

public class StrategyTest {

    @Test
    public void testBloodPressureStrategyTriggersHighSystolic() {
        PatientRecord record = new PatientRecord(1, 150.0, "BloodPressureSystolic", 123456789L);
        AlertStrategy strategy = new BloodPressureStrategy();

        assertTrue(strategy.checkAlert(record));
    }

    @Test
    public void testOxygenSaturationStrategyDoesNotTrigger() {
        PatientRecord record = new PatientRecord(1, 95.0, "SpO2", 123456789L);
        AlertStrategy strategy = new OxygenSaturationStrategy();

        assertFalse(strategy.checkAlert(record));
    }
}
