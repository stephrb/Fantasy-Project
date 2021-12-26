public class MatchupImpl implements Matchup {
  private double homePoints;
  private double awayPoints;
  private int homeTeamId;
  private int awayTeamId;
  private int winnerTeamId;

  public MatchupImpl(int homeTeamId, double homePoints, int awayTeamId, double awayPoints) {
    this.homeTeamId = homeTeamId;
    this.homePoints = homePoints;
    this.awayTeamId = awayTeamId;
    this.awayPoints = awayPoints;
    if (isBye() || homePoints == awayPoints) winnerTeamId = -1;
    else if (homePoints > awayPoints) winnerTeamId = homeTeamId;
    else winnerTeamId = awayTeamId;
  }

  public int getHomeTeamId() {
    return homeTeamId;
  }

  public int getAwayTeamId() {
    return awayTeamId;
  }

  public double getAwayPoints() {
    return awayPoints;
  }

  public double getHomePoints() {
    return homePoints;
  }

  @Override
  public boolean isBye() {
    return awayPoints == -1;
  }

  public int getWinnerTeamId() {
    return winnerTeamId;
  }

  public void setWinnerTeamId(int winnerTeamId) {
    this.winnerTeamId = winnerTeamId;
  }
}
