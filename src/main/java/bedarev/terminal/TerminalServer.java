package bedarev.terminal;

import bedarev.input_and_print.InputInterface;
import bedarev.input_and_print.Menu;
import bedarev.input_and_print.UserInput;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class TerminalServer {
    private int account = 0;
    private Menu menu;
    private InputInterface userInput = new UserInput();
    private RandomException randomException = new RandomException();

    public TerminalServer(Menu menu) {
        this.menu = menu;
    }

    public int getAccount() {
        return account;
    }

    private void setAccount(int account) {
        this.account = account;
    }

    public void calcValue(String message, BiFunction<Integer, Integer, Integer> biFunction, String input) {
        //Проверка на кратность ста и на положительный результат лябмды
        if ((Integer.valueOf(input) % 100 == 0) && ((biFunction.apply(account, Integer.valueOf(input))) > 0)) {
            setAccount(biFunction.apply(account, Integer.valueOf(input)));
            menu.print(message + Integer.valueOf(input) +  ". Total: " + account);
        }

        if ((biFunction.apply(account, Integer.valueOf(input))) < 0) {
            throw new NotEnoughMoneyException();
        }

        if (Integer.valueOf(input) % 100 != 0) {
            menu.print("Enter number multiple one hundred");
        }
    }

    public void runTerminal () throws Exception {
        while (true) {
            menu.showMenu();
            try {
                randomException.random();
                String input = userInput.getInput();
                if (input.equals("4")) {
                    break;
                }
                logicMenu(input);
                menu.printPressEnter();

            } catch (NumberFormatException e) {
                menu.print("Error. Please enter nubmer");
                menu.printPressEnter();
            } catch (NotEnoughMoneyException exception) {
                menu.printPressEnter();
            }
        }
    }

    public void logicMenu(String menuInput) {
        InputInterface userInput = new UserInput();
        Map<String,Runnable> menuValueMap = new HashMap<>();
        menu.print("Please enter value: ");

        menuValueMap.put("1",() ->
            menu.print("On your account: " + getAccount())
        );

        menuValueMap.put("2", () ->
                calcValue("Add to your account: ", (x, y) -> x + y, userInput.getInput()));

        menuValueMap.put("3", () ->
                calcValue("Withdraw from your account: ", (x, y) -> x - y, userInput.getInput()));

        if(menuValueMap.containsKey(menuInput)) {
            menuValueMap.get(menuInput).run();
        }
    }

}


