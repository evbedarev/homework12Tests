package bedarev.terminal;

import java.util.Random;

public class RandomException {
    public void random () throws NetworkProblemException {
        Random random = new Random();
        if (random.nextInt(10) == 2) {
            throw new NetworkProblemException("Network problems. Please try later...");
        }

        if (random.nextInt(10) == 3) {
            throw new NetworkProblemException("Hardware problems.  Please try later...");
        }
    }

}

class NetworkProblemException extends Exception {
    NetworkProblemException(String message) {
        super(message);
    }
}

class HardwareProblemException extends Exception {
    HardwareProblemException(String message) {
        super(message);
    }
}