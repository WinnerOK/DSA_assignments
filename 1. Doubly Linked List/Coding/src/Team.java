public class Team {

    static class Comparator implements java.util.Comparator<Team> {

        @Override
        public int compare(Team o1, Team o2) {
            if (o1.getPoints_earned() != o2.getPoints_earned())
                return o1.getPoints_earned() - o2.getPoints_earned();

            if (o1.getWins() != o2.getWins())
                return o1.getWins() - o2.getWins();

            if (o1.getGoal_difference() != o2.getGoal_difference())
                return o1.getGoal_difference() - o2.getGoal_difference();

            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        }
    }

    private final static int POINTS_FOR_WIN = 3, POINTS_FOR_DRAW = 1;

    private String name;
    private int points_earned;
    private int wins, draws, loses;
    private int goal_difference;
    private int games_played;
    private int scored, missed;

    public void printStat(int rank) {
        System.out.printf("%d) %s %sp, %dg (%d-%d-%d), %dgd (%d-%d)\n", rank, name, points_earned,
                games_played, wins, draws, loses, goal_difference,
                scored, missed);
    }

    public String getName() {
        return name;
    }

    public int getPoints_earned() {
        return points_earned;
    }

    public int getWins() {
        return wins;
    }

    public int getGoal_difference() {
        return goal_difference;
    }

    public Team(String name) {
        this.name = name;
        points_earned = 0;
        wins = 0;
        goal_difference = 0;
        games_played = 0;
        draws = 0;
        loses = 0;
        scored = 0;
        missed = 0;
    }

    public void addDifference(int scored_this, int scored_other) {
        goal_difference += scored_this - scored_other;
        scored += scored_this;
        missed += scored_other;
    }

    public void addWin() {
        wins++;
        points_earned += Team.POINTS_FOR_WIN;
        games_played++;
    }

    public void addDraw() {
        draws++;
        points_earned += Team.POINTS_FOR_DRAW;
        games_played++;
    }

    public void addLose() {
        loses++;
        games_played++;
    }
}
