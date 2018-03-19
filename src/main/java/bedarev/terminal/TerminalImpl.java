package bedarev.terminal;

import bedarev.input_and_print.InputInterface;
import bedarev.input_and_print.Menu;
import bedarev.input_and_print.UserInput;
import bedarev.pin_processor.PinValidator;

public class TerminalImpl {
    private final TerminalServer server;
    private final PinValidator pinValidator;
    private Menu menu = new Menu();
    private InputInterface userInput = new UserInput();


    private TerminalImpl(TerminalServer server, PinValidator pinValidator) {
        this.server = server;
        this.pinValidator = pinValidator;
    }


    private void run() throws InterruptedException {
        while (true) {
            menu.print("Please enter pin code. For quit press 'q': ");
            try {
                String input = userInput.getInput();
                if (input.equals("q")) {
                    break;
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
        }
        userInput.closeConnection();
    }



    public static void main(String[] args) throws InterruptedException {
        TerminalImpl terminal = new TerminalImpl(new TerminalServer(), new PinValidator());
        terminal.run();

    }
}