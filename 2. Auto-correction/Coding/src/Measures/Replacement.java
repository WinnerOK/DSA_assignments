package Measures;

public class Replacement implements EditDistanceMeasurer {

    private double cost;

    public Replacement(double cost) {
        this.cost = cost;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public double estimate(double[][] dynamic, String word1, String word2, int i, int j) {
        return dynamic[i - 1][j - 1] + getCost();
    }
}
