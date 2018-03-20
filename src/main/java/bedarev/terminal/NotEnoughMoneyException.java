package bedarev.terminal;

public class NotEnoughMoneyException extends Error {
    NotEnoughMoneyException() {
        super("Not enough money. ");
    }
}