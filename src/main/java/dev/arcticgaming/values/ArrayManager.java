package dev.arcticgaming.values;

import java.util.ArrayList;

public class ArrayManager {
    ArrayList<Integer> rollList = new ArrayList<>();

    public void addRoll(int newRoll) {
        rollList.add(newRoll);
    }

    public ArrayList<Integer> getRollList(){
        return rollList;
    }
}
