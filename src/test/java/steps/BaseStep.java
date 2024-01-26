package steps;

import java.util.Random;

public abstract class BaseStep {
    public String getRandomProjectId() {
        return String.valueOf(new Random().nextInt(1000000000));
    }
}