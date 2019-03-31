package Measures;

public class Transposition implements EditDistanceMeasurer {
    private double cost;

    public Transposition(double cost) {
        this.cost = cost;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public double estimate(double[][] dynamic, String word1, String word2, int i, int j) {
        return (i > 2 && j > 2 && word1.charAt(i - 2) == word2.charAt(j - 3)
                && word1.charAt(i - 3) == word2.charAt(j - 2) ?
                dynamic[i - 2][j - 2] + getCost() :
                IMPOSSIBLE);
    }
}
