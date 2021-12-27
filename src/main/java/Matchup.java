public interface Matchup {
  int getHomeTeamId();

  int getAwayTeamId();

  double getAwayPoints();

  double getHomePoints();

  boolean isBye();

  int getWinnerTeamId();

  void setWinnerTeamId(int winnerTeamId);

  boolean getIsPlayed();

  boolean isTie();
}
