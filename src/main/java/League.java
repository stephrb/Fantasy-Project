import java.util.List;
import java.util.Map;
import java.util.Set;

public interface League {
  /** @return a set of teams */
  Set<Team> getTeams();

  /**
   * @param teamId the id of the team
   * @return the team corresponding to the given ID
   */
  Team getTeam(int teamId);

  /**
   * @param team the team to add to the league
   * @param id the ID of the team
   */
  void addTeam(int id, Team team);

  /** @param year the year of the league */
  void setYear(int year);

  /** @return the calendar year that the league is in */
  int getYear();

  /** @return the ESPN league id */
  String getLeagueId();

  /** @param currentMatchupPeriod the matchup period of the league */
  void setCurrentMatchupPeriod(int currentMatchupPeriod);

  /** @return the matchup period of the league */
  int getCurrentMatchupPeriod();

  /** @param currentScoringPeriod the scoring period of the league */
  void setCurrentScoringPeriod(int currentScoringPeriod);

  /** @return the scoring period of the league */
  int getCurrentScoringPeriod();

  /**
   * @param player the player to add to the team
   * @param teamId the teamId of the team
   */
  void addPlayer(Player player, int teamId);

  /** @param freeAgents the list of free agents of the league */
  void setFreeAgents(List<Player> freeAgents);

  /** @return the list of free agents of the league */
  List<Player> getFreeAgents();

  /**
   * @param matchupPeriod the matchup period that the matchup is in
   * @param matchup the matchup for the given matchup period
   */
  void addMatchup(int matchupPeriod, Matchup matchup);

  /**
   * @param compareTeamId the team id for the team that will compare its schedule to the schedule team
   * @param scheduleTeamId the team id for the schedule team to be compared to
   * @return an array of size 3 where 0 is wins, 1 is losses, and 2 is ties
   */
  int[] compareSchedules(int compareTeamId, int scheduleTeamId);

  /**
   * @param matchupPeriod the matchupPeriod for the calculation
   * @param teamId the team to do the calculation for
   * @return the record of the team against every other team in the league for a given week
   */
  int[] weeklyRecord(int matchupPeriod, int teamId);

  Map<Integer, Double> getMedianPointsPerWeek();

  Double getMedianPointsPerWeek(int matchupPeriod);
  /**
   * @param teamId the id of the team
   * @return the power ranking score (Points Scored + (Points Scored * Winning %) + (Points Scored vs the median score of the week)
   */
  double getPowerRankingScore(int teamId);
}
