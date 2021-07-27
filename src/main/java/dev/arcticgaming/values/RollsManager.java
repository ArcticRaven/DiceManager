package dev.arcticgaming.values;

import java.util.Random;

public class RollsManager {

    private int rollsRemaining = 0;
    private int lastRollValue = 0;

    public int getRollsRemaining() {
        return rollsRemaining;
    }

    public void setRollsRemaining(int currentRolls,int args) {
        this.rollsRemaining = currentRolls + args;
    }

    public int getLastRollValue() {
        return lastRollValue;
    }

    public int makeRolls(int faces){
        Random generator = new Random();
        double rollValue = generator.nextInt(faces)+1;
        int value = (int)rollValue;
        this.lastRollValue = value;

        return value;
    }
}
