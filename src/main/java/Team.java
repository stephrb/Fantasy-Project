import java.util.List;
import java.util.Map;

public interface Team {

  /** @return list of players */
  List<Player> getPlayers();

  /** @param player the player to add to the team */
  void addPlayer(Player player);

  int getTeamId();

  /** @return the team name */
  String getName();

  /**
   * @param matchupPeriod the integer of the matchup period for the match
   * @param matchup the matchup object that encapsulates data about the match
   */
  void addMatchup(int matchupPeriod, Matchup matchup);

  /** @return the matchups map for a the team */
  Map<Integer, Matchup> getMatchups();

  /**
   * @param matchupPeriod the matchup period
   * @return the points for in a given matchup period
   */
  double getPointsFor(int matchupPeriod);

  /**
   * @param matchupPeriod the matchup period
   * @return the points against in a given matchup period
   */
  double getPointsAgainst(int matchupPeriod);

  Map<Integer, Double> getPointsForPerWeek(int currentMatchupPeriod);

  Map<Integer, Double> getPointsAgainstPerWeek(int currentMatchupPeriod);


}
