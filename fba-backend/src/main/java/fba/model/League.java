package fba.model;

import fba.model.player.Player;
import fba.model.team.DraftPick;
import fba.model.team.Matchup;
import fba.model.team.Team;
import org.json.simple.JSONObject;

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

  void setFinalScoringPeriod(int finalScoringPeriod);

  int getFinalScoringPeriod();
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
   * @param compareTeamId the team id for the team that will compare its schedule to the schedule
   *     team
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
   * @return the power ranking score (Points Scored + (Points Scored * Winning %) + (Points Scored
   *     vs the median score of the week)
   */
  double getPowerRankingScore(int teamId);

  List<Team> getPowerRankings();

  Map<String, Map<Integer, boolean[]>> getProTeamMatchups();

  void setProTeamMatchups(Map<String, Map<Integer, boolean[]>> proTeamMatchups);
  List<Map<String, List<String>>> getScheduleComparison();
  List<Map<String, List<String>>> getWeeklyComparison();

  /**
   * @param timePeriod time period that the players average score is taken into account. acceptable
   *     values are: "Last_15_2022", "Last_30_2022", "Projected_2022", "Season_2022", "Projected_2021", "Season_2021",
   *     "Last_7_2022"
   * @param matchupPeriod the matchupPeriod that the scores are requested for
   * @param assessInjuries whether injuries should be taken into account
   * @return a list of JSON objects for each team that holds data about the projected scores
   */
  List<JSONObject> getProjectedScores(String timePeriod, int matchupPeriod, boolean assessInjuries);
  List<JSONObject> getProTeamGames(int matchupPeriod);
  Double getWinPercentage(int homeTeam, int awayTeam, int numGames, int matchupPeriod, boolean assessInjuries);
  List<String> getRosteredPlayerIds();
  void setAllPlayers(Map<String, Player> allPlayers);
  Map<String, Player> getAllPlayers();
  void setDraftPicks(Map<Integer, DraftPick> draftPicks);
  public Map<Integer, DraftPick> getDraftPicks();
 }
