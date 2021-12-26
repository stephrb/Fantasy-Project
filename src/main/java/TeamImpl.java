import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamImpl implements Team {

  private List<Player> players;
  private final String nickname;
  private final String location;
  private final String abbrev;
  private final int teamId; // The playerId of the espn user
  private int wins; // Number of wins
  private int losses; // Number of losses
  private int ties;
  private final double pointsFor; // Total points for
  private final double pointsAgainst; // Total points against
  private double gamesBack; // Number of games back from 1st
  private Map<Integer, Integer>
      matchUpAcquisitionsPerWeek; // Keys are matchup weeks and values are number of acquisitions
  private final int moveToActive;
  private final int drops;
  private int playoffSeed;
  private final int totalAcquisitions;
  private final int divisionId;
  private Map<Integer, Matchup> matchups;

  public TeamImpl(
      String nickname,
      String location,
      String abbrev,
      int teamId,
      int wins,
      int losses,
      int ties,
      double pointsFor,
      double pointsAgainst,
      double gamesBack,
      Map<Integer, Integer> matchUpAcquisitionsPerWeek,
      int moveToActive,
      int drops,
      int playoffSeed,
      int totalAcquisitions,
      int divisionId) {
    this.nickname = nickname;
    this.location = location;
    this.abbrev = abbrev;
    this.teamId = teamId;
    this.wins = wins;
    this.losses = losses;
    this.ties = ties;
    this.pointsFor = pointsFor;
    this.pointsAgainst = pointsAgainst;
    this.gamesBack = gamesBack;
    this.matchUpAcquisitionsPerWeek = matchUpAcquisitionsPerWeek;
    this.moveToActive = moveToActive;
    this.drops = drops;
    this.playoffSeed = playoffSeed;
    this.totalAcquisitions = totalAcquisitions;
    this.divisionId = divisionId;
    players = new ArrayList<>();
    matchups = new HashMap<>();
  }

  @Override
  public List<Player> getPlayers() {
    return players;
  }

  @Override
  public void addPlayer(Player player) {
    players.add(player);
  }

  public int getTeamId() {
    return teamId;
  }

  public String getName() {
    return location + " " + nickname;
  }

  @Override
  public void addMatchup(int matchupPeriod, Matchup matchup) {
    matchups.put(matchupPeriod, matchup);
  }

  @Override
  public Map<Integer, Matchup> getMatchups() {
    return matchups;
  }

  @Override
  public double getPointsFor(int matchupPeriod) {
    Matchup matchup = matchups.get(matchupPeriod);
    if (teamId == matchup.getHomeTeamId()) return matchup.getHomePoints();
    else return matchup.getAwayPoints();
  }

  @Override
  public double getPointsAgainst(int matchupPeriod) {
    Matchup matchup = matchups.get(matchupPeriod);
    if (teamId == matchup.getHomeTeamId()) return matchup.getAwayPoints();
    else return matchup.getHomePoints();
  }

  @Override
  public Map<Integer, Double> getPointsForPerWeek(int currentMatchupPeriod) {
    Map<Integer, Double> weeklyPointsFor = new HashMap<>();
    for (int i = 1; i < currentMatchupPeriod; i++) {
      weeklyPointsFor.put(i, getPointsFor(i));
    }
    return weeklyPointsFor;
  }

  @Override
  public Map<Integer, Double> getPointsAgainstPerWeek(int currentMatchupPeriod) {
    Map<Integer, Double> weeklyPointsAgainst = new HashMap<>();
    for (int i = 1; i < currentMatchupPeriod; i++) {
      weeklyPointsAgainst.put(i, getPointsAgainst(i));
    }
    return weeklyPointsAgainst;
  }

  @Override
  public Team clone() {
    Team team = new TeamImpl(
            nickname,
            location,
            abbrev,
            teamId,
            wins,
            losses,
            ties,
            pointsFor,
            pointsAgainst,
            gamesBack,
            matchUpAcquisitionsPerWeek,
            moveToActive,
            drops,
            playoffSeed,
            totalAcquisitions,
            divisionId);
    team.setPlayers(players);
    team.setMatchups(matchups);
    return team;

  }

  @Override
  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  @Override
  public void setMatchups(Map<Integer, Matchup> matchups) {
    this.matchups = matchups;
  }

  @Override
  public int getWins() {
    return wins;
  }

  @Override
  public void setWins(int wins) {
    this.wins = wins;
  }

  @Override
  public int getLosses() {
    return losses;
  }

  @Override
  public void setLosses(int losses) {
    this.losses = losses;
  }

  @Override
  public int getTies() {
    return ties;
  }

  @Override
  public void setTies(int ties) {
    this.ties = ties;
  }

  @Override
  public double getWinPercentage() {
    return ((double)(wins * 2 + ties)) / ((double)(2 * (wins + ties + losses)));
  }
}
