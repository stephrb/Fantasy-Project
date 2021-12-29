package fba.model;

import fba.model.team.Matchup;
import fba.model.team.Team;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PlayoffMachine {
  void sortRankings();

  List<Team> getRankings();

  Map<Integer, Set<Matchup>> getMatchups();

  void setWinner(Matchup matchup, int winnerTeamId);

  void printRankings();
}
