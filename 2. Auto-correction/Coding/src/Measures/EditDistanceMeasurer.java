package Measures;

public interface EditDistanceMeasurer {

     double IMPOSSIBLE = Double.POSITIVE_INFINITY;

    /**
     * @return A cost of implementing operation
     */
    double getCost();

    /**
     * @param dynamic A current state of dynamic in algorithm
     * @param word1 The Word from which distance is being calculated
     * @param word2 The Word to what distance is being calculated
     * @param i Current position into a dynamic (row)
     * @param j Current position into a dynamic (column)
     * @return an estimated cost to perform a correction by given algorithm
     */
    double estimate(double[][] dynamic, String word1, String word2, int i, int j);
}
