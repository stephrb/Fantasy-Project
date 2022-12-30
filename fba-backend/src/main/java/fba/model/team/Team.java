package fba.model.team;

import fba.model.player.Player;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public interface Team {

    /**
     * @return list of players
     */
    List<Player> getPlayers();

    void setPlayers(List<Player> players);

    /**
     * @param player the player to add to the team
     */
    void addPlayer(Player player);

    /**
     * @return the id of the team
     */
    int getTeamId();

    /**
     * @return the team name
     */
    String getName();

    void setName(String name);

    /**
     * @param matchupPeriod the integer of the matchup period for the match
     * @param matchup       the matchup object that encapsulates data about the match
     */
    void addMatchup(int matchupPeriod, Matchup matchup);

    /**
     * @return the matchups map for a the team
     */
    Map<Integer, Matchup> getMatchups();

    void setMatchups(Map<Integer, Matchup> matchups);

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

    /**
     * @param currentMatchupPeriod the matchup period the league is currently in
     * @return a map where the keys are matchup periods up to the current matchup and the values are
     * the number of points that a team scored in that week
     */
    Map<Integer, Double> getPointsForPerWeek(int currentMatchupPeriod);

    /**
     * @param currentMatchupPeriod the matchup period the league is currently in
     * @return a map where the keys are matchup periods up to the current matchup and the values are
     * the number of points that a team's opponent scored in that week
     */
    Map<Integer, Double> getPointsAgainstPerWeek(int currentMatchupPeriod);

    /**
     * @return returns a new instance of the team object
     */
    Team copy();

    int getWins();

    void setWins(int wins);

    int getLosses();

    void setLosses(int losses);

    int getTies();

    void setTies(int ties);

    double getWinPercentage();

    int getPlayoffSeed();

    int getDivisionId();

    int[] getHeadToHeadRecord(int teamId);

    double getPointsFor();

    double getPowerRankingScore();

    void setPowerRankingScore(double powerRankingScore);

    String getRecord();

    double getAvgPointsForTeam(String timePeriod);

    Map<Player, Map<DayOfWeek, Boolean>> getDailyLineups(int matchupPeriod, boolean assessInjuries, int numRecentGames, int currentMatchupPeriod, int currentScoringPeriod);
}
