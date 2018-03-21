package bedarev.terminal;

import java.util.Random;

public class RandomException {
    public void random () throws NetworkProblemException, HardwareProblemException {
        Random random = new Random();
        if (random.nextInt(10) == 2) {
            throw new NetworkProblemException("Network problems. Please try later...");
        }

        if (random.nextInt(10) == 3) {
            throw new HardwareProblemException("Hardware problems.  Please try later...");
        }
    }

}

