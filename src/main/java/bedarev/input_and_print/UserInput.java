package bedarev.input_and_print;

import java.util.Scanner;

public class UserInput implements InputInterface {
    private Scanner scanner;

    public UserInput() {
       scanner = new Scanner(System.in);
    }

    public String getInput() {
        return scanner.nextLine();
    }
}