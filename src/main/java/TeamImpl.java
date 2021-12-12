import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeamImpl implements Team {

  private List<Player> players;
  private final String nickname;
  private final String location;
  private final String abbrev;
  private final int teamId; // The playerId of the espn user
  private final int wins; // Number of wins
  private final int losses; // Number of losses
  private final double pointsFor; // Total points for
  private final double pointsAgainst; // Total points against
  private final double gamesBack; // Number of games back from 1st
  private Map<Integer, Integer>
      matchUpAcquisitionsPerWeek; // Keys are matchup weeks and values are number of acquisitions
  private final int moveToActive;
  private final int drops;
  private final int playoffSeed;
  private final int totalAcquisitions;

  public TeamImpl(
      String nickname,
      String location,
      String abbrev,
      int teamId,
      int wins,
      int losses,
      double pointsFor,
      double pointsAgainst,
      double gamesBack,
      Map<Integer, Integer> matchUpAcquisitionsPerWeek,
      int moveToActive,
      int drops,
      int playoffSeed,
      int totalAcquisitions) {
    this.nickname = nickname;
    this.location = location;
    this.abbrev = abbrev;
    this.teamId = teamId;
    this.wins = wins;
    this.losses = losses;
    this.pointsFor = pointsFor;
    this.pointsAgainst = pointsAgainst;
    this.gamesBack = gamesBack;
    this.matchUpAcquisitionsPerWeek = matchUpAcquisitionsPerWeek;
    this.moveToActive = moveToActive;
    this.drops = drops;
    this.playoffSeed = playoffSeed;
    this.totalAcquisitions = totalAcquisitions;
    players = new ArrayList<>();
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
}
