package alerts;
import com.alerts.Alert;
import com.alerts.AlertFactory;
import com.alerts.BloodPressureAlertFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlertFactoryTest {

    @Test
    public void testBloodPressureAlertFactoryCreatesCorrectAlert() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("P001", "High BP", 123456789L);

        assertNotNull(alert);
        assertEquals("P001", alert.getPatientId());
        assertEquals("High BP", alert.getCondition());
        assertEquals(123456789L, alert.getTimestamp());
    }
}
