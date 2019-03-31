/*
 * [S19] Data Structures and Algorithms,
 * Assignment 1.
 * The Author: Daniil Manakovskiy (d.manakovskiy@innopolis.university)
 * Group: BS18-02
 *
 * Tests for Data Structures are provided with code coverage 100%
 * "Tournament ranking" is tested by CodeForces system
 * https://codeforces.com/group/lk8Ud0ZeBu/contest/238197/submission/49833973
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {
        /*
        Everywhere through this code instead of scanner.nextInt() will be used Integer.parseInt(scanner.nextLine())
        The reason is that nextInt doesn't read \n symbol after int, thus using nextLine after it will return "\n"
         */
        Scanner scanner = new Scanner(new FileInputStream("input.txt"));
        int n = Integer.parseInt(scanner.nextLine());
        while (n-- > 0) { // create ranking for n tournaments
            solve(scanner);
        }
    }

    static void solve(Scanner scanner) {

        System.out.println(scanner.nextLine()); // read tournament name and print it immediately
        int teams_total = Integer.parseInt(scanner.nextLine());
        ArrayedList<Team> teams = new ArrayedList<>(teams_total);

        //map team_name to index in order to access the object of team in O(1) instead of linear while searching in List
        Map<String, Integer> team_indexes = new HashMap<>(teams_total);

        for (int i = 0; i < teams_total; i++) {
            String team_name = scanner.nextLine();
            teams.addLast(new Team(team_name));
            team_indexes.put(team_name, i);
        }

        int total_matches = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < total_matches; i++) {
            String[] match = scanner.nextLine().split("[#:]");
            calculateMetrics(teams.get(team_indexes.get(match[0])), Integer.parseInt(match[1]),
                    Integer.parseInt(match[2]), teams.get(team_indexes.get(match[3])));
        }

        teams.sort(new Team.Comparator());

        for (int i = 0; i < teams_total; i++) {
            Team t = teams.get(i);
            t.printStat(i + 1);
        }
        System.out.println();
    }

    static void calculateMetrics(Team A, int scoreA, int scoreB, Team B) {
        int goal_difference = scoreA - scoreB;
        A.addDifference(scoreA, scoreB);
        B.addDifference(scoreB, scoreA);

        if (goal_difference > 0) {
            A.addWin();
            B.addLose();
        } else if (goal_difference < 0) {
            A.addLose();
            B.addWin();
        } else {
            A.addDraw();
            B.addDraw();
        }
    }

}


