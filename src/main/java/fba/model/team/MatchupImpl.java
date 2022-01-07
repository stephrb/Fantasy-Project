package fba.model.team;

public class MatchupImpl implements Matchup {
  private final double homePoints;
  private final double awayPoints;
  private final int homeTeamId;
  private final int awayTeamId;
  private int winnerTeamId;
  private boolean isPlayed;
  private final String homeTeamName;
  private final String awayTeamName;
  private final int matchupId;
  private final int matchupPeriod;

  public MatchupImpl(
      int homeTeamId, double homePoints, int awayTeamId, double awayPoints, boolean isPlayed, String homeTeamName, String awayTeamName, int matchupId, int matchupPeriod) {
    this.homeTeamId = homeTeamId;
    this.homePoints = homePoints;
    this.awayTeamId = awayTeamId;
    this.awayPoints = awayPoints;
    if (isBye() || homePoints == awayPoints) winnerTeamId = -1;
    else if (homePoints > awayPoints) winnerTeamId = homeTeamId;
    else winnerTeamId = awayTeamId;
    this.isPlayed = isPlayed;
    this.homeTeamName = homeTeamName;
    this.awayTeamName = awayTeamName;
    this.matchupId = matchupId;
    this.matchupPeriod = matchupPeriod;
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
  public void setIsPlayed(boolean isPlayed) {
    this.isPlayed = isPlayed;
  }

  @Override
  public boolean isTie() {
    return isPlayed && !isBye() && winnerTeamId == -1;
  }

  @Override
  public String getWinnerTeamName() {
    if (winnerTeamId == homeTeamId) return homeTeamName;
    if (winnerTeamId == awayTeamId) return awayTeamName;
    return null;
  }

  @Override
  public String getHomeTeamName() {
    return homeTeamName;
  }

  @Override
  public String getAwayTeamName() {
    return awayTeamName;
  }

  @Override
  public void setWinnerHome() {
    this.winnerTeamId = homeTeamId;
  }

  @Override
  public void setWinnerAway() {
    if (awayTeamId == -1) throw new IllegalStateException();
    this.winnerTeamId = awayTeamId;
  }

  @Override
  public int getMatchupId() {
    return matchupId;
  }

  @Override
  public int getMatchupPeriod() {
    return matchupPeriod;
  }

  @Override
  public Matchup copy() {
    return new MatchupImpl(homeTeamId, homePoints, awayTeamId, awayPoints, isPlayed, homeTeamName, awayTeamName, matchupId, matchupPeriod);
  }


}
