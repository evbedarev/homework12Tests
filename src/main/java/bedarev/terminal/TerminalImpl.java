package bedarev.terminal;

import bedarev.input_and_print.InputInterface;
import bedarev.input_and_print.Menu;
import bedarev.input_and_print.UserInput;
import bedarev.pin_processor.PinValidator;

public class TerminalImpl {
    private final TerminalServer server;
    private final PinValidator pinValidator;
    private Menu menu = new Menu();

    public TerminalImpl(TerminalServer server, PinValidator pinValidator) {
        this.server = server;
        this.pinValidator = pinValidator;
    }

    public boolean run(String input) throws InterruptedException {
        try {
            if (input.equals("q")) {
                return true;
            }
            if (pinValidator.validatePin(input)) {
                server.runTerminal();
            }
        } catch (NetworkProblemException | HardwareProblemException exception) {
            menu.print(exception.getMessage());
            Thread.sleep(3000);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        Menu menu = new Menu();
        InputInterface userInput = new UserInput();
        TerminalImpl terminal = new TerminalImpl(new TerminalServer(menu), new PinValidator(menu));
        boolean exit = false;

        while (!exit) {
            menu.print("Please enter pin code. For quit press 'q': ");
            exit = terminal.run(userInput.getInput());
        }
    }
}