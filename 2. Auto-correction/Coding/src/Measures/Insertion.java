package Measures;

public class Insertion implements EditDistanceMeasurer {

    private double cost;

    public Insertion(double cost) {
        this.cost = cost;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public double estimate(double[][] dynamic, String word1, String word2, int i, int j) {
        return dynamic[i][j - 1] + getCost();
    }
}
