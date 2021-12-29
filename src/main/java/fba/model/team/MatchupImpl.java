package fba.model.team;

public class MatchupImpl implements Matchup {
  private double homePoints;
  private double awayPoints;
  private int homeTeamId;
  private int awayTeamId;
  private int winnerTeamId;
  private boolean isPlayed;

  public MatchupImpl(
      int homeTeamId, double homePoints, int awayTeamId, double awayPoints, boolean isPlayed) {
    this.homeTeamId = homeTeamId;
    this.homePoints = homePoints;
    this.awayTeamId = awayTeamId;
    this.awayPoints = awayPoints;
    if (isBye() || homePoints == awayPoints) winnerTeamId = -1;
    else if (homePoints > awayPoints) winnerTeamId = homeTeamId;
    else winnerTeamId = awayTeamId;
    this.isPlayed = isPlayed;
  }

  @Override
  public int getHomeTeamId() {
    return homeTeamId;
  }

  @Override
  public int getAwayTeamId() {
    return awayTeamId;
  }

  @Override
  public double getAwayPoints() {
    return awayPoints;
  }

  @Override
  public double getHomePoints() {
    return homePoints;
  }

  @Override
  public boolean isBye() {
    return awayPoints == -1;
  }

  @Override
  public int getWinnerTeamId() {
    return winnerTeamId;
  }

  @Override
  public void setWinnerTeamId(int winnerTeamId) {
    if (this.winnerTeamId == winnerTeamId && isPlayed) return;
    isPlayed = true;
    this.winnerTeamId = winnerTeamId;
  }

  @Override
  public boolean getIsPlayed() {
    return isPlayed;
  }

  @Override
  public boolean isTie() {
    return isPlayed && !isBye() && winnerTeamId == -1;
  }
}
