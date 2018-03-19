import bedarev.pin_processor.PinValidator;
import org.junit.Assert;
import org.junit.Test;

public class TestPinValidator extends Assert{
    private final static String wrongPinCode = "12345";
    private final static String PinCode = "1234";

    @Test
    public void testPinValidator() {
        assertFalse(new PinValidator().validatePin(wrongPinCode));
        assertTrue(new PinValidator().validatePin(PinCode));

    }
}
