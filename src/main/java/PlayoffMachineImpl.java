import java.util.*;

public class PlayoffMachineImpl implements PlayoffMachine {
  private List<Team> rankings;
  private int currentMatchupPeriod;
  private int finalMatchupPeriod;
  private final Map<Integer, Set<Matchup>> matchups;
  private Map<Integer, Double> winPercentages;

  public PlayoffMachineImpl(League league) {
    rankings = new ArrayList<>();
    for (Team team : league.getTeams()) {
      rankings.add(team.clone());
    }
    currentMatchupPeriod = league.getCurrentMatchupPeriod();
    finalMatchupPeriod =
        league.getTeam(league.getTeams().iterator().next().getTeamId()).getMatchups().size();
    matchups = new HashMap<>();
    for (int i = currentMatchupPeriod; i <= finalMatchupPeriod; i++) {
      matchups.put(i, new HashSet<>());
      for (Team team : rankings) {
        if (matchups.get(i).contains(team.getMatchups().get(i))) continue;
        if (!team.getMatchups().get(i).isBye()) matchups.get(i).add(team.getMatchups().get(i));
      }
    }
  } 


}
