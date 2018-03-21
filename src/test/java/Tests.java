import bedarev.input_and_print.Menu;
import bedarev.pin_processor.PinValidator;
import bedarev.terminal.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class Tests {
    private static final String WRONG_PIN_CODE = "12345";
    private static final String VALID_PIN_CODE = "1234";
    private PinValidator pinValid;
    private TerminalServer termServ;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testPinValidator() {
        Menu menu = mock(Menu.class);
        PinValidator pinValidator = new PinValidator(menu);
        assertFalse(new PinValidator(new Menu()).validatePin(WRONG_PIN_CODE));
        assertTrue(new PinValidator(new Menu()).validatePin(VALID_PIN_CODE));
        for (int i = 0; i < 4; i++) {
            pinValidator.validatePin(WRONG_PIN_CODE);
        }
        assertTrue(pinValidator.isAccountIsLocked());
        verify(menu, times(1)).print("Account is locked. Time left: 0 seconds");
    }

    @Test
    public void testCalculateValue() {
        TerminalServer terminalServer = new TerminalServer(new Menu());
        assertTrue(terminalServer.getAccount() == 0);
        terminalServer.calcValue("1", (x,y) -> x + y, "1200");
        assertTrue(terminalServer.getAccount() == 1200);
        terminalServer.calcValue("1", (x,y) -> x + y, "120");
        assertTrue(terminalServer.getAccount() == 1200);
        exception.expect(NumberFormatException.class);
        exception.expectMessage("For input string: \"qwe\"");
        terminalServer.calcValue("1", (x,y) -> x + y, "qwe");
    }

    @Test
    public void testNotEnoughMoneyException() {
        TerminalServer terminalServer = new TerminalServer(new Menu());
        exception.expect(NotEnoughMoneyException.class);
        exception.expectMessage("Not enough money. ");
        terminalServer.calcValue("1", (x,y) -> x - y,"1400");
    }

    @Test
    public void testTerminalImpl() throws Exception {
        pinValid = mock(PinValidator.class);
        TerminalImpl terminal = new TerminalImpl(termServ, pinValid);
        terminal.run("12345");
        verify(pinValid,times(1)).validatePin("12345");
        terminal.run("1234");
        verify(pinValid,times(1)).validatePin("1234");
        terminal.run("q");
        verifyZeroInteractions(pinValid);
    }

    @Test
    public void testTerminal() throws Exception {
        pinValid = new PinValidator(new Menu());
        termServ = mock(TerminalServer.class);
        TerminalImpl terminal = new TerminalImpl(termServ, pinValid);
        terminal.run("12347");
        verify(termServ,times(0)).runTerminal();
        terminal.run("1234");
        verify(termServ,times(1)).runTerminal();
    }

    @Test
    public void testLogicMenu() {
        Menu menu = mock(Menu.class);
        termServ = new TerminalServer(menu);
        termServ.logicMenu("1");
        verify(menu,times(1)).print("Please enter value: ");
        verify(menu,times(1)).print("On your account: 0");
    }

    @Test
    public void testNetworkException() throws NetworkProblemException {
        RandomException randomException = new RandomException();
        exception.expect(NetworkProblemException.class);
        for (int i = 0; i < 100; i++) {
            try {
                randomException.random();
            } catch (HardwareProblemException exception) {
                exception.getMessage();
            }
        }
    }

    @Test
    public void testHardwareException() throws HardwareProblemException {
        RandomException randomException = new RandomException();
        exception.expect(HardwareProblemException.class);
        for (int i = 0; i < 100; i++) {
            try {
                randomException.random();
            } catch (NetworkProblemException exception) {
                exception.getMessage();
            }
        }
    }
}
