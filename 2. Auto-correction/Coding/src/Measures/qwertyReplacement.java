package Measures;

import java.util.HashMap;


public class qwertyReplacement implements EditDistanceMeasurer {

    private double cost;
    // hashmap from character to its neighbours
    final HashMap<Character, String> neighbours = new HashMap<>() {
        {
            put('a', "qwsz");
            put('b', "vghn");
            put('c', "xdfv");
            put('d', "serfcx");
            put('e', "wsdr");
            put('f', "drtgvc");
            put('g', "ftyhbv");
            put('h', "gyujnb");
            put('i', "ujko");
            put('j', "huikmn");
            put('k', "jiolm");
            put('l', "pok");
            put('m', "njk");
            put('n', "bhjm");
            put('o', "iklp");
            put('p', "ol");
            put('q', "wsa");
            put('r', "edft");
            put('s', "wedxza");
            put('t', "rfgy");
            put('u', "yhji");
            put('v', "cfgb");
            put('w', "qase");
            put('x', "zsdc");
            put('y', "tghu");
            put('z', "asx");
        }
    };

    public qwertyReplacement(double cost) {
        this.cost = cost;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public double estimate(double[][] dynamic, String word1, String word2, int i, int j) {
        // check whether at some position
        if (i > 1 & j > 1){
            if (neighbours.getOrDefault(word1.charAt(i - 2), "").indexOf(word2.charAt(j - 2)) != -1)
                return dynamic[i-1][j-1] + cost;
        }
        return IMPOSSIBLE;
    }
}
