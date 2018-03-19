package task_3_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class RenderPageContent {

/*
*Принимает строку, сверяет с шаблоном.
 */
    private static void readPage (String url) throws IOException {
        String line;
        if (url.matches("^(?:https?://)(?:(?:\\w+)\\.)+(?:\\w){2,3}[A-Za-z0-9.,\\-%/;_]*")) {
            URL link = new URL(url);
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(link.openStream())
            )) {
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        String input;
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Please enter link or 'q' for exit: ");
                input = scanner.nextLine();
                if (input.equals("q")) {
                    break;
                }
                readPage(input);
            }
        } catch (IOException mf) {
            mf.printStackTrace();
        }
    }

}
