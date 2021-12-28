package Model;

import Model.Team.*;

import java.util.*;

public class PlayoffMachineImpl implements PlayoffMachine {
  private List<Team> rankings;
  private int currentMatchupPeriod;
  private int finalMatchupPeriod;
  private final Map<Integer, Set<Matchup>> matchups;

  public PlayoffMachineImpl(League league) {
    rankings = new ArrayList<>();
    for (Team team : league.getTeams()) {
      rankings.add(team.copy());
    }
    sortRankings();

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

  public void sortRankings() {
    rankings.sort(
        (Team o1, Team o2) -> {
          if (o2.getWinPercentage() - o1.getWinPercentage() != 0)
            return (int) (o2.getWinPercentage() * 100 - o1.getWinPercentage() * 100);
          int[] h2hRecord = o1.getHeadToHeadRecord(o2.getTeamId());
          if (h2hRecord[0] != h2hRecord[1]) return h2hRecord[1] - h2hRecord[0];
          if (o1.getPointsFor() != o2.getPointsFor())
            return (int) (o2.getPointsFor() - o1.getPointsFor());
          return (int) (Math.random() * 2 - 1);
        });
    Set<Integer> divisions = new HashSet<>();
    for (int i = 0; i < rankings.size(); i++) {
      Team team = rankings.get(i);
      if (divisions.add(team.getDivisionId())) {
        rankings.remove(team);
        rankings.add(divisions.size() - 1, team);
      }
    }
  }

  @Override
  public List<Team> getRankings() {
    return rankings;
  }

  @Override
  public Map<Integer, Set<Matchup>> getMatchups() {
    return matchups;
  }

  @Override
  public void setWinner(Matchup matchup, int winnerTeamId) {
    if (winnerTeamId == matchup.getWinnerTeamId() && matchup.getIsPlayed()) return;

    for (Team team : rankings) {
      if (team.getTeamId() == matchup.getHomeTeamId()
          || team.getTeamId() == matchup.getAwayTeamId()) {
        if (matchup.isTie()) team.setTies(team.getTies() - 1);
        else if (matchup.getIsPlayed()) {
          if (team.getTeamId() == matchup.getWinnerTeamId()) team.setWins(team.getWins() - 1);
          else team.setLosses(team.getLosses() - 1);
        }
        if (team.getTeamId() == winnerTeamId) team.setWins(team.getWins() + 1);
        else if (winnerTeamId > 0) team.setLosses(team.getLosses() + 1);
        else team.setTies(team.getTies() + 1);
      }
    }
    matchup.setWinnerTeamId(winnerTeamId);
  }

  @Override
  public void printRankings() {
    sortRankings();
    int rank = 1;
    for (Team team : rankings)
      System.out.println(
          rank++
              + "\t"
              + team.getName()
              + " "
              + team.getWins()
              + "-"
              + team.getLosses()
              + "-"
              + team.getTies());
  }
}
