package fba.model.team;

public interface Matchup {
  int getHomeTeamId();

  int getAwayTeamId();

  double getAwayPoints();

  double getHomePoints();

  boolean isBye();

  int getWinnerTeamId();

  void setWinnerTeamId(int winnerTeamId);

  boolean getIsPlayed();

  void setIsPlayed(boolean isPlayed);

  boolean isTie();

  String getWinnerTeamName();

  String getHomeTeamName();

  String getAwayTeamName();

  void setWinnerHome();

  void setWinnerAway();

  int getMatchupId();

  int getMatchupPeriod();

  Matchup copy();
}
