package fba.model;

import fba.model.team.Matchup;
import fba.model.team.MatchupImpl;
import fba.model.team.Team;

import java.util.*;

public class PlayoffMachineImpl implements PlayoffMachine {
  private List<Team> rankings;
  private List<Team> startingRankings;
  private final int currentMatchupPeriod;
  private final int finalMatchupPeriod;
  private Map<Integer, Set<Matchup>> matchups;
  private boolean isSorted;

  public PlayoffMachineImpl(League league) {
    rankings = new ArrayList<>();
    for (Team team : league.getTeams()) {
      rankings.add(team.copy());
    }
    sortRankings();
    startingRankings = cloneRankings(rankings);
    currentMatchupPeriod = league.getCurrentMatchupPeriod();
    finalMatchupPeriod =
        league.getTeam(league.getTeams().iterator().next().getTeamId()).getMatchups().size();
    matchups = new HashMap<>();
    for (int i = currentMatchupPeriod; i <= finalMatchupPeriod; i++) {
      matchups.put(i, new HashSet<>());
      for (Team team : rankings) {
        if (matchups.get(i).contains(team.getMatchups().get(i))) continue;
        if (!team.getMatchups().get(i).isBye()) {
          if (i == currentMatchupPeriod) {
            team.getMatchups().get(i).setWinnerTeamId(-1);
            team.getMatchups().get(i).setIsPlayed(false);
          }
          matchups.get(i).add(team.getMatchups().get(i));
        }
      }
    }
    isSorted = true;
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
    isSorted = true;
  }

  @Override
  public List<Team> getRankings() {

    if (!isSorted) sortRankings();
    return rankings;
  }

  @Override
  public Map<Integer, Set<Matchup>> getMatchups() {
    return matchups;
  }

  @Override
  public Map<String, Set<Matchup>> getMatchupsJson() {
    Map<String, Set<Matchup>> matchupsJson = new HashMap<>();
    for (Map.Entry<Integer, Set<Matchup>> entry : matchups.entrySet()) {
      matchupsJson.put(String.valueOf(entry.getKey()), entry.getValue());
    }
    return matchupsJson;
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
  public void setWinnerHome(int matchupPeriod, int matchupId) {
    isSorted = false;
    for (Matchup matchup : matchups.get(matchupPeriod)) {
      if (matchup.getMatchupId() == matchupId) {
        setWinner(matchup, matchup.getHomeTeamId());
        break;
      }
    }
  }

  @Override
  public void setWinnerAway(int matchupPeriod, int matchupId) {
    isSorted = false;
    for (Matchup matchup : matchups.get(matchupPeriod)) {
      if (matchup.getMatchupId() == matchupId) {
        setWinner(matchup, matchup.getAwayTeamId());
        break;
      }
    }
  }

  @Override
  public void setWinnerTie(int matchupPeriod, int matchupId) {
    isSorted = false;
    for (Matchup matchup : matchups.get(matchupPeriod)) {
      if (matchup.getMatchupId() == matchupId) {
        setWinner(matchup, -1);
        break;
      }
    }
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

  @Override
  public List<String> getRemainingMatchupPeriods() {
    List<String> remainingMatchups = new ArrayList<>();
    for (int i = currentMatchupPeriod; i <= finalMatchupPeriod; i++) {
      remainingMatchups.add(String.valueOf(i));
    }
    return remainingMatchups;
  }

  @Override
  public boolean getIsSorted() {
    return isSorted;
  }

  @Override
  public void resetPlayoffMachine() {
    rankings = startingRankings;
    resetMatchups();
    startingRankings = cloneRankings(rankings);
  }


  private void resetMatchups() {
    for (Set<Matchup> set : matchups.values()) {
      for (Matchup matchup : set) {
        matchup.setWinnerTeamId(-1);
        matchup.setIsPlayed(false);
      }
    }
  }

  private List<Team> cloneRankings(List<Team> rankings) {
    List<Team> clonedRankings = new ArrayList<>();
    for (Team team : rankings) clonedRankings.add(team.copy());
    return clonedRankings;
  }
}
