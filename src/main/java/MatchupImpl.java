public class MatchupImpl implements Matchup {
  private double homePoints;
  private double awayPoints;
  private int homeTeamId;
  private int awayTeamId;

  public MatchupImpl(int homeTeamId, double homePoints, int awayTeamId, double awayPoints) {
    this.homeTeamId = homeTeamId;
    this.homePoints = homePoints;
    this.awayTeamId = awayTeamId;
    this.awayPoints = awayPoints;
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
}
