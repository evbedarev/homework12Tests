package bedarev.terminal;

import bedarev.input_and_print.InputInterface;
import bedarev.input_and_print.Menu;
import bedarev.input_and_print.UserInput;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;


public class TerminalServer {
    private int account = 0;
    private Menu menu = new Menu();
    private InputInterface userInput = new UserInput();
    private RandomException randomException = new RandomException();


    private int getAccount() {
        return account;
    }

    private void setAccount(int account) {
        this.account = account;
    }


    private void calcValue(String message, BiFunction<Integer, Integer, Integer> biFunction) {
        menu.print("Please enter value: ");
        String input = userInput.getInput();  //Ввод суммы которую хотим внести или снять

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
        menu.printPressEnter();
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
            } catch (NumberFormatException e) {
                menu.print("Error. Please enter nubmer");
                menu.printPressEnter();
            } catch (NotEnoughMoneyException exception) {
                menu.printPressEnter();
            }
        }
    }



    private void logicMenu(String input) {
        Map<String,Runnable> menuValueMap = new HashMap<>();
        menuValueMap.put("1",() -> {
            menu.print("On your account: " + getAccount());
            menu.printPressEnter();
        });
        menuValueMap.put("2", () ->
                calcValue("Add to your account: ", (x, y) -> x + y));
        menuValueMap.put("3", () ->
                calcValue("Withdraw from your account: ", (x, y) -> x - y));
        if(menuValueMap.containsKey(input)) {
            menuValueMap.get(input).run();
        }
    }

}


class NotEnoughMoneyException extends Error {
    NotEnoughMoneyException() {
        super("Not enough money. ");
    }
}