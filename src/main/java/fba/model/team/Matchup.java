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

  boolean isTie();

  String getWinnerTeamName();

  String getHomeTeamName();

  String getAwayTeamName();

  void setWinnerHome();

  void setWinnerAway();

  int getMatchupId();

  int getMatchupPeriod();
}
