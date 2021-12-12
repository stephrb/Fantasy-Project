import java.util.*;

public class LeagueImpl implements League {
  private int year;
  private Map<Integer, Team> teams;
  private final String leagueId;
  private int currentMatchupPeriod;
  private int currentScoringPeriod;
  private final String name;
  private List<Player> freeAgents;
  public LeagueImpl(String leagueId, String name) {
    teams = new HashMap<>();
    this.leagueId = leagueId;
    this.name = name;
  }

  @Override
  public Set<Team> getTeams() {
    return new HashSet<>((teams.values()));
  }

  @Override
  public void addTeam(int id, Team team) {
    teams.put(id, team);
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public int getYear() {
    return year;
  }

  @Override
  public String getLeagueId() {
    return leagueId;
  }

  @Override
  public void setCurrentMatchupPeriod(int currentMatchupPeriod) {
    this.currentMatchupPeriod = currentMatchupPeriod;
  }

  @Override
  public int getCurrentMatchupPeriod() {
    return currentMatchupPeriod;
  }

  @Override
  public void setCurrentScoringPeriod(int currentScoringPeriod) {
    this.currentScoringPeriod = currentScoringPeriod;
  }

  @Override
  public int getCurrentScoringPeriod() {
    return currentScoringPeriod;
  }

  @Override
  public void addPlayer(Player player, int id) {
    teams.get(id).addPlayer(player);
  }

  public void setFreeAgents(List<Player> freeAgents) {
    this.freeAgents = freeAgents;
  }

  @Override
  public List<Player> getFreeAgents() {
    return freeAgents;
  }
}
