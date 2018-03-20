import bedarev.pin_processor.PinValidator;
import bedarev.terminal.NotEnoughMoneyException;
import bedarev.terminal.TerminalImpl;
import bedarev.terminal.TerminalServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;



public class TestPinValidator extends Assert{
    private final static String wrongPinCode = "12345";
    private final static String PinCode = "1234";
    private PinValidator pinValid;
    private TerminalServer termServ;

    @Test
    public void testPinValidator() {
        PinValidator pinValidator = new PinValidator();
        assertFalse(new PinValidator().validatePin(wrongPinCode));
        assertTrue(new PinValidator().validatePin(PinCode));

        for (int i = 0; i < 3; i++) {
            pinValidator.validatePin(wrongPinCode);
        }
        assertTrue(pinValidator.isAccountIsLocked());
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();
    public ExpectedException exceptionNumber = ExpectedException.none();

    @Test
    public void testTerminalServer() {
        //calcValue
        TerminalServer terminalServer = new TerminalServer();
        assertTrue(terminalServer.getAccount() == 0);

        terminalServer.calcValue("1", (x,y) -> x + y, "1200");
        assertTrue(terminalServer.getAccount() == 1200);

        terminalServer.calcValue("1", (x,y) -> x + y, "120");
        assertTrue(terminalServer.getAccount() == 1200);

        exceptionNumber.expect(NumberFormatException.class);
        exception.expectMessage("For input string: \"qwe\"");
        terminalServer.calcValue("1", (x,y) -> x + y, "qwe");



    }

    @Test
    public void testNotEnoughMoneyException() {
        TerminalServer terminalServer = new TerminalServer();
        exception.expect(NotEnoughMoneyException.class);
        exception.expectMessage("Not enough money. ");
        terminalServer.calcValue("1", (x,y) -> x - y,"1400");

    }
//
    @Test
    public void testLogicMenu() {
        TerminalServer terminalServer = new TerminalServer();

        terminalServer.logicMenu("2", "1000");
        assertTrue(terminalServer.getAccount() == 1000);

        terminalServer.logicMenu("3", "100");
        assertTrue(terminalServer.getAccount() == 900);

    }

    @Before
    public void setUp() {
        pinValid = Mockito.mock(PinValidator.class);
        termServ = Mockito.mock(TerminalServer.class);
    }


    @Test
    public void testTerminalImpl() throws InterruptedException {
        TerminalImpl terminal = new TerminalImpl(termServ, pinValid);
        terminal.run("12345");
//        Mockito.verifyNoMoreInteractions(pinValid,1);
        Mockito.verifyZeroInteractions(termServ);
        terminal.run("1234");
        Mockito.verifyNoMoreInteractions(termServ);
    }
}
