public interface Matchup {
    int getHomeTeamId();
    int getAwayTeamId();
    double getAwayPoints();
    double getHomePoints();
    boolean isBye();
}
