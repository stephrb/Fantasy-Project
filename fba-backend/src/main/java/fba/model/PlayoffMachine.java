package fba.model;

import fba.model.team.Matchup;
import fba.model.team.Team;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PlayoffMachine {
    void sortRankings();

    List<Team> getRankings();

    List<Team> getStartingRankings();

    Map<Integer, Set<Matchup>> getPlayoffMachineMatchups();

    Map<String, Set<Matchup>> getMatchupsJson();

    void setWinner(Matchup matchup, int winnerTeamId);

    void setWinnerHome(int matchupPeriod, int matchupId);

    void setWinnerAway(int matchupPeriod, int matchupId);

    void setWinnerTie(int matchupPeriod, int matchupId);

    void printRankings();

    List<String> getRemainingMatchupPeriods();

    boolean getIsSorted();

    void resetPlayoffMachine();

}
