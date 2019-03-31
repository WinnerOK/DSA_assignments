package Measures;

import java.util.HashMap;

public class PhoneticReplacement implements EditDistanceMeasurer {
    private double cost;
    final HashMap<Character, String> replacements = new HashMap<>() {
        {
            put('f', "ph");
            put('k', "ck");
            put('v', "wh");
            //th has different sound depending on context of word
            //put("z", "th");
        }
    };


    public PhoneticReplacement(double cost) {
        this.cost = cost;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public double estimate(double[][] dynamic, String word1, String word2, int i, int j) {
        // k-th symbol in the string = i - 2 , l-th = j - 2
        // take k-th symbol, if substring l-1..l of another word is equal to a corresponding replacement to k-th sum
        // then it is possible to go from 0..l-2 - 0..k-1 to 0..l-0..k with 1 replacement. The same is applicable mirrorly
        if (i > 1 && j > 2) {
            char possible_substitution_in_1 = word1.charAt(i - 2);

            if (word2.substring(j - 3, j - 1).equals(replacements.getOrDefault(possible_substitution_in_1, ""))) {
                return dynamic[i - 1][j - 2] + getCost();
            }
        }
        if (i > 2 && j > 1) {
            char possible_substitution_in_2 = word2.charAt(j - 2);
            if (word1.substring(i - 3, i - 1).equals(replacements.getOrDefault(possible_substitution_in_2, ""))) {
                return dynamic[i - 2][j - 1] + getCost();
            }
        }


        return IMPOSSIBLE;
    }
}
