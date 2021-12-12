import java.util.List;
import java.util.Set;

public interface League {
  /** @return a list of teams */
  Set<Team> getTeams();

  /**
   * @param team the team to add to the league
   * @param id the ID of the team
   */
  void addTeam(int id, Team team);

  /** @param year the year of the league */
  void setYear(int year);

  int getYear();

  String getLeagueId();

  void setCurrentMatchupPeriod(int currentMatchupPeriod);

  int getCurrentMatchupPeriod();

  void setCurrentScoringPeriod(int currentScoringPeriod);

  int getCurrentScoringPeriod();

  /**
   * @param player the player to add to the team
   * @param id the id of the team
   */
  void addPlayer(Player player, int id);

  void setFreeAgents(List<Player> freeAgents);

  List<Player> getFreeAgents();
}
