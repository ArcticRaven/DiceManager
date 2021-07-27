package dev.arcticgaming.values;

public class SumManager {

    private int sum = 0; //establishes sum as 0

    public int getSum() {
        return sum;
    }

    public void setSum(int newSum,int args) {
        this.sum =newSum+args;
    }
}
