package bedarev.input_and_print;

import java.io.IOException;

public class Menu {

    public final void showMenu() {
        System.out.println("=====================");
        System.out.println("Please enter command:");
        System.out.println("1 - check account");
        System.out.println("2 - add money");
        System.out.println("3 - take money");
        System.out.println("4 - quit");
        System.out.println("=====================");
    }

    public final void print(String message) {
        System.out.println(message);
    }

    public final void printPressEnter() {
        try {
            System.out.println("Press enter to continue ...");
            System.in.read();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

}