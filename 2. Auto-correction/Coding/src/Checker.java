import Measures.EditDistanceMeasurer;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Pattern;

public class Checker {
    // HashMap is used for the dictionary to store frequency of every word for better correction on texts
    //Linked - because iteration over all entries is O(1)
    private LinkedHashMap<String, Integer> dict_with_freq;
    // Pattern of what is called 'word' - is the assignment it is a sequence of lowercase letters
    private final Pattern wordPattern = Pattern.compile("[a-z]+");

    public Checker(String to_dict) {
        if (to_dict == null) throw new NullPointerException("Dictionary must be not null");
        dict_with_freq = new LinkedHashMap<>();
        readDictionary(to_dict);
    }

    //costs for different operations. They are added to make code more readable, since costs are not nameless constants
    private int DELETION_COST = 1;
    private int INSERTION_COST = 1;
    private int REPLACEMENT_COST = 1;
    private int TRANSPOSITION_COST = 1;

    private boolean isWord(String word) {
        return wordPattern.matcher(word).matches();
    }

    /**
     * Split str to letters and non-letters
     *
     * @param str String to be tokenized
     * @return List with structure [some_word], [some_punctuation], [some_word] etc
     */
    private LinkedList<String> tokenize(String str) {
        //LinkedList - we don't know how many tokens given string will produce
        //final structure of tokens: [some_word], [some_punctuation], [some_word] etc
        LinkedList<String> tokens = new LinkedList<>();
        //Builds a word from chars
        StringBuilder currentWord = new StringBuilder();
        boolean is_word = true; // does currentWord contain a word(true) or punctuation/non-letters(false)
        for (int i = 0; i < str.length(); i++) {
            if (is_word) {
                // if current char supposed to be a part of word, but it is not a letter
                if (!Character.isLowerCase(str.charAt(i))) {
                    // finish current token, and say that next chars are supposed to be non-letters
                    tokens.addLast(currentWord.toString());
                    currentWord.setLength(0);
                    is_word = false;
                }
            } else {
                // same procedure as above, but for non-letters and letters
                if (Character.isLowerCase(str.charAt(i))) {
                    tokens.addLast(currentWord.toString());
                    currentWord.setLength(0);
                    is_word = true;
                }
            }
            currentWord.append(str.charAt(i));
        }
        // last token is never proceed within a loop because after it string immediately ends without changing
        // from letter to non-letters (and vice versa)
        tokens.addLast(currentWord.toString());
        return tokens;
    }

    /**
     * Parses given string into a dictionary
     *
     * @param str text from which dictionary will be extracted
     */
    private void readDictionary(String str) {
        if (!str.isEmpty()) {
            LinkedList<String> tokens = tokenize(str);
            for (String i : tokens) {
                // if current token is a word, it contains letters only
                if (Character.isLetter(i.charAt(0))) {
                    dict_with_freq.put(i, dict_with_freq.getOrDefault(i, 0) + 1);
                }
            }
        }

    }

    /**
     * @param word word to make a suggestion
     * @return a LinkedList of best corrections in alphabetic order
     */
    public LinkedList<String> suggestCorrection(String word) {
        if (this.dict_with_freq.isEmpty())
            throw new IllegalStateException("You can't use suggesrCorrection with empty dictionary");
        if (word == null)
            throw new IllegalArgumentException("Word must be non-empty string");
        if (!isWord(word)) {
            throw new IllegalArgumentException("Argument must contain exactly 1 word");
        }
        return suggestCorrection(word, false);
    }

    /**
     * performs a correction on given string according to the dictionary passed into a constructor
     *
     * @param str String to be corrected
     * @return Corrected string
     */
    public String autoCorrect(String str) {
        if (str == null)
            throw new IllegalArgumentException("str must be non-null string");

        // Builds the corrected string
        StringBuilder corrected_string = new StringBuilder();
        LinkedList<String> tokens = tokenize(str);
        for (String i : tokens) {
            // if current token is a word
            if (Character.isLetter(i.charAt(0))) {
                //perform a correction, returns a word with the less editDistance and the greatest frequency in dict
                corrected_string.append(suggestCorrection(i, true).getFirst());
            } else {
                // otherwise it is a punctuation or space - add it as it is
                corrected_string.append(i);
            }
        }
        return corrected_string.toString();
    }

    /**
     * Return all/(the best) possible correction to word (depends on value of best_only)
     *
     * @param word      word to be corrected
     * @param best_only Whether return one best correction (by frequency) or all
     * @return LinkedList of correction suggest
     */
    private LinkedList<String> suggestCorrection(String word, Boolean best_only) {
        // The algorithm is using brute-force technique: calculates the editDistance between given word and every other
        // word in the dictionary, keeping the best word(-s).

        // min_dist - current minimum edit distance
        int min_dist = Integer.MAX_VALUE;
        // best_corrections contains entries of dictionary in order to perform comparison between matches
        // to choose the best one if best_only is true
        LinkedList<Map.Entry<String, Integer>> best_corrections_freq = new LinkedList<>();
        for (Map.Entry<String, Integer> known_word : this.dict_with_freq.entrySet()) {
            int dist = estimate(word, known_word.getKey());
            // if distance improved, we should forget all previous corrections
            if (dist < min_dist) {
                min_dist = dist;
                best_corrections_freq.clear();
            }

            //if distance improved or stayed the same
            //add a suggestion or choose the best one (depends on best_only)
            if (dist == min_dist) {
                if (best_only) {
                    // if only one element with maximum frequency required
                    if (best_corrections_freq.size() == 0) {
                        // if there is no elements, just add the first one
                        best_corrections_freq.addLast(known_word);
                    } else {
                        //otherwise retrieve the first element and compare with current
                        //by frequency
                        Map.Entry<String, Integer> current = best_corrections_freq.getFirst();
                        if (current.getValue() < known_word.getValue()) {
                            // if frequency of new word is greater, replace first with the current
                            best_corrections_freq.clear();
                            best_corrections_freq.addFirst(known_word);
                        }
                    }
                } else {
                    // if all corrections are required, just add all
                    best_corrections_freq.addLast(known_word);
                }

            }
        }
        LinkedList<String> result = new LinkedList<>();
        for (Map.Entry<String, Integer> corrections : best_corrections_freq) {
            result.addLast(corrections.getKey());
        }
        //Since dictionary is a LinkedHashMap we can't guarantee alphabetical order without sorting
        Collections.sort(result);
        return result;
    }


    /**
     * Calculates an editDistance between 2 given words
     *
     * @param word1 word from which you calculation of distance starts
     * @param word2 word to what calculation of distance is being performed
     * @return the distance between 2 given words
     */
    @SuppressWarnings("Duplicates")
    public int estimate(String word1, String word2) {
        /*
        The implemented algorithm of finding Damerau-Levenstain distance is based on the principle of optimality
        in sub-sections, that states: if at every sub-section of 1..n the optimal result is achieved then the optimal
        result for 1..n can be calculated through those sub-sequences.

        Such principle is usually applied in dynamic programming approach (which is naturally applicable in given problem)
         */
        if (word1 == null || word2 == null)
            throw new IllegalArgumentException("word1 and word2 must be not-null");
        if (!isWord(word1) || !isWord(word2))
            throw new IllegalArgumentException("Both arguments must contain a single word");

        //if one word is empty, we need just add {other_word}.length symbols
        if (word1.isEmpty()) {
            return word2.length() * INSERTION_COST;
        } else {
            if (word2.isEmpty()) {
                return word1.length() * INSERTION_COST;
            }
        }

        // Matrix distance stores at distance[i][j] minimum edit distance from word1[0..i] to word[0..j]
        // All strings is being filled from index 1 for more natural understanding
        int[][] distance = new int[word1.length() + 2][word2.length() + 2];


        // base cases for the dynamic are situations where one sub-string is empty
        // in such cases we should either add symbols or delete them
        for (int i = 0; i <= word1.length(); i++) {
            distance[i + 1][1] = i * DELETION_COST;
        }

        for (int j = 0; j <= word2.length(); j++) {
            distance[1][j + 1] = j * INSERTION_COST;
        }


        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // if at some positions 2 symbols are the same, then sub-strings of current strings should not be modified
                    distance[i + 1][j + 1] = distance[i][j];
                } else {
                    // otherwise it is possible to replace one letter with another
                    distance[i + 1][j + 1] = distance[i][j] + REPLACEMENT_COST;
                }
                // in all different cases we can either insert 1 symbol into shorter sub-string, or add - to longer, or
                distance[i + 1][j + 1] = min(
                        distance[i + 1][j + 1],
                        distance[i + 1][j] + INSERTION_COST,
                        distance[i][j + 1] + DELETION_COST);
                // -or transpose 2 adjacent symbols if they are adjacent in both strings
                if (i > 1 && j > 1 && word1.charAt(i - 1) == word2.charAt(j - 2) && word1.charAt(i - 2) == word2.charAt(j - 1)) {
                    distance[i + 1][j + 1] = Integer.min(
                            distance[i + 1][j + 1],
                            distance[i - 1][j - 1] + TRANSPOSITION_COST
                    );
                }
            }
        }
        return distance[word1.length() + 1][word2.length() + 1];
    }

    /**
     * Calculates an editDistance between 2 given words
     *
     * @param word1 word from which you calculation of distance starts
     * @param word2 word to what calculation of distance is being performed
     * @param estimators List of EditDistanceMeasurer objects, that will perform calculation of editDistance at every step
     * @return the distance between 2 given words or -1 if it is impossible to get from word1 to word2 with given
     * estimators
     */
    @SuppressWarnings("Duplicates")
    public double estimate(String word1, String word2, LinkedList<EditDistanceMeasurer> estimators) {
       /*
        The implemented algorithm of finding Damerau-Levenstain distance is based on the principle of optimality
        in sub-sections, that states: if at every sub-section of 1..n the optimal result is achieved then the optimal
        result for 1..n can be calculated through those sub-sequences.

        Such principle is usually applied in dynamic programming approach (which is naturally applicable in given problem)
         */
        if (word1 == null || word2 == null)
            throw new IllegalArgumentException("word1 and word2 must be not-null");
        if (!isWord(word1) || !isWord(word2))
            throw new IllegalArgumentException("Both arguments must contain a single word");
        if (estimators.size() == 0)
            throw new IllegalArgumentException("You must provide at least 1 estimator");


        // Matrix distance stores at distance[i][j] minimum edit distance from word1[0..i] to word[0..j]
        // All strings is being filled from index 1 for more natural understanding
        double[][] distance = new double[word1.length() + 2][word2.length() + 2];

        // no base cases are provided since all costs determines by another algorithm

        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // if at some positions 2 symbols are the same, then sub-strings of current strings should not be modified
                    distance[i + 1][j + 1] = distance[i][j];
                } else {
                    double min = Double.MAX_VALUE;
                    for (EditDistanceMeasurer estimator : estimators) {
                        min = Double.min(min, estimator.estimate(distance, word1, word2, i + 1, j + 1));
                    }
                    distance[i + 1][j + 1] = min;
                }
            }
        }
        return (distance[word1.length() + 1][word2.length() + 1] != Double.MAX_VALUE) ? distance[word1.length() + 1][word2.length() + 1] : -1;
    }

    private static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

}
