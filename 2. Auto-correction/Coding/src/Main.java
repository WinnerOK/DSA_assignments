/**
 * Created by Daniil Manakovskiy
 * Innopolis University student
 * BS18-02
 * Date: 05.03.19
 *
 * Links to the CodeForces submissions:
 * Task 1: https://codeforces.com/group/lk8Ud0ZeBu/contest/239900/submission/50862463
 * Task 2: https://codeforces.com/group/lk8Ud0ZeBu/contest/239900/submission/50862665
 * Task 3: https://codeforces.com/group/lk8Ud0ZeBu/contest/239900/submission/50862682
 */

import Measures.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
//        long start = System.nanoTime();

//        distanceFromPair(); // task 1
//        bestCorrection(); // task 2
        autoCorrect(); // task 3
//        additionalTasks();


//        System.out.println((System.nanoTime() - start) / 1000000000.0);
    }

    static void bestCorrection() throws FileNotFoundException {
//        Scanner scanner = new Scanner(new FileInputStream("input.txt"));
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        Checker checker = new Checker(scanner.nextLine());
        for (String str : checker.suggestCorrection(scanner.nextLine())) {
            System.out.print(str + " ");
        }

    }

    static void distanceFromPair() throws FileNotFoundException {
//        Scanner scanner = new Scanner(new FileInputStream("input.txt"));
        Scanner scanner = new Scanner(System.in);
        Checker checker = new Checker("");
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            String[] str = scanner.nextLine().split(" ");
            String w1, w2;
            w1 = str[0];
            w2 = str[1];
            System.out.println(checker.estimate(w1, w2));
        }
    }

    static void autoCorrect() throws FileNotFoundException {
//        Scanner scanner = new Scanner(new FileInputStream("input.txt"));
        Scanner scanner = new Scanner(System.in);
        Checker checker = new Checker(scanner.nextLine());
        System.out.println(checker.autoCorrect(scanner.nextLine()));
    }

    /**
     * Flexibility of grammar rules is provided by the interface Measures.EditDistanceMeasurer
     * Programmer must create a class that implements Measures.EditDistanceMeasurer and pass them as LinkedList to
     * method estimate
     *
     * The proper flexibility would be achieved if list of estimators was passed to the Checker constructor.
     * I did not implement it because method estimate must return int according to the task, but some estimators has
     * cost of type double. Without such restriction, I could create a method that would call an appropriate version of
     * method estimate depending on size of estimators list (call standard if list is empty, customized - otherwise)
     */
    static void additionalTasks() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("input.txt"));
//        Scanner scanner = new Scanner(System.in);
        Checker checker = new Checker("");
        LinkedList<EditDistanceMeasurer> estimators = new LinkedList<>(Arrays.asList(
//                new Deletion(1), new Insertion(1), new Replacement(1), new Transposition(1)
//                new PhoneticReplacement(1)
                new qwertyReplacement(0.5)
        ));
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            String[] str = scanner.nextLine().split(" ");
            String w1, w2;
            w1 = str[0];
            w2 = str[1];
            System.out.println(checker.estimate(w1,w2) + " " + checker.estimate(w1, w2, estimators));
        }

    }

    public static String  get2DArrayPrint(double[][] arr){
        StringBuilder str = new StringBuilder();
        for (double[] i: arr){
            for (double j: i){
                str.append(j).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }

}

