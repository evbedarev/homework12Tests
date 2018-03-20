package bedarev.pin_processor;
import bedarev.input_and_print.Menu;

public class PinValidator {
    private final static String pinCode = "1234";
    private int countInputPin = 1;
    private boolean accountIsLocked = false;
    private int timer;
    private Menu menu = new Menu();
    private final static Integer MAX_CNT_INCORRECT_PIN_INPUT = 3;

    public boolean validatePin(String userPinCode) {
        try {
            if (accountIsLocked && countInputPin == MAX_CNT_INCORRECT_PIN_INPUT) {
                throw new AccountIsLockedException("Account is locked. Time left: " + getTimer() + " seconds");
            }

            if (userPinCode.equals(pinCode)) {
                menu.print("Pin is valid");
            }

            if (!userPinCode.equals(pinCode)) {
                failedAttemptsCounter();
            }

            return userPinCode.equals(pinCode);

        } catch (AccountIsLockedException exception) {
            menu.print(exception.getMessage());
        }
        return false;
    }

    public boolean isAccountIsLocked() {
        return accountIsLocked;
    }

    private int getCountInputPin() {
        return countInputPin;
    }

    private void setCountInputPin(int countInputPin) {
        this.countInputPin = countInputPin;
    }

    private int getTimer() {
        return timer;
    }

    private void setAccountIsLocked() {
        accountIsLocked = true;
        new Thread(() ->{
            try {
                for (timer = 5;timer > 0;timer--) {
                    Thread.sleep(1000);
                }
                accountIsLocked = false;
                countInputPin = 0;
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    private void failedAttemptsCounter() {

        if (getCountInputPin() >= MAX_CNT_INCORRECT_PIN_INPUT) {
            menu.print("Account is locked.");
            setAccountIsLocked();
        }

        if (getCountInputPin() < MAX_CNT_INCORRECT_PIN_INPUT) {
            setCountInputPin(getCountInputPin() + 1);
            menu.print("Pin is not valid, please try more...");
        }
    }
}

class AccountIsLockedException extends Error {
    AccountIsLockedException(String message) {
        super(message);
    }
}