package fba.model;

import fba.model.player.Player;
import fba.model.proteams.ProTeamGame;
import fba.model.team.DraftPick;
import fba.model.team.Matchup;
import fba.model.team.Team;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModelImpl implements Model {
  private final League league;
  private final PlayoffMachine playoffMachine;

  public ModelImpl(League league, PlayoffMachine playoffMachine) {
    this.league = league;
    this.playoffMachine = playoffMachine;
  }

  @Override
  public Set<Team> getTeams() {
    return league.getTeams();
  }

  @Override
  public Team getTeam(int teamId) {
    return league.getTeam(teamId);
  }

  @Override
  public void addTeam(int id, Team team) {
    league.addTeam(id, team);
  }

  @Override
  public void setYear(int year) {
    league.setYear(year);
  }

  @Override
  public int getYear() {
    return league.getYear();
  }

  @Override
  public String getLeagueId() {
    return league.getLeagueId();
  }

  @Override
  public void setCurrentMatchupPeriod(int currentMatchupPeriod) {
    league.setCurrentMatchupPeriod(currentMatchupPeriod);
  }

  @Override
  public int getCurrentMatchupPeriod() {
    return league.getCurrentMatchupPeriod();
  }

  @Override
  public void setCurrentScoringPeriod(int currentScoringPeriod) {
    league.setCurrentScoringPeriod(currentScoringPeriod);
  }

  @Override
  public int getCurrentScoringPeriod() {
    return league.getCurrentScoringPeriod();
  }

  @Override
  public void setFinalScoringPeriod(int finalScoringPeriod) {
     league.setFinalScoringPeriod(finalScoringPeriod);
  }

  @Override
  public int getFinalScoringPeriod() {
    return league.getFinalScoringPeriod();
  }

  @Override
  public void addPlayer(Player player, int teamId) {
    league.addPlayer(player, teamId);
  }

  @Override
  public void setFreeAgents(List<Player> freeAgents) {
    league.setFreeAgents(freeAgents);
  }

  @Override
  public List<Player> getFreeAgents() {
    return league.getFreeAgents();
  }

  @Override
  public void addMatchup(int matchupPeriod, Matchup matchup) {
    league.addMatchup(matchupPeriod, matchup);
  }

  @Override
  public int[] compareSchedules(int compareTeamId, int scheduleTeamId) {
    return league.compareSchedules(compareTeamId, scheduleTeamId);
  }

  @Override
  public int[] weeklyRecord(int matchupPeriod, int teamId) {
    return league.weeklyRecord(matchupPeriod, teamId);
  }

  @Override
  public Map<Integer, Double> getMedianPointsPerWeek() {
    return league.getMedianPointsPerWeek();
  }

  @Override
  public Double getMedianPointsPerWeek(int matchupPeriod) {
    return league.getMedianPointsPerWeek(matchupPeriod);
  }

  @Override
  public double getPowerRankingScore(int teamId) {
    return league.getPowerRankingScore(teamId);
  }

  @Override
  public List<Team> getPowerRankings() {
    return league.getPowerRankings();
  }

  @Override
  public Map<String, Map<Integer, ProTeamGame>> getProTeamMatchups() {
    return league.getProTeamMatchups();
  }

  @Override
  public void setProTeamMatchups(Map<String, Map<Integer, ProTeamGame>> proTeamMatchups) {
    league.setProTeamMatchups(proTeamMatchups);
  }

  @Override
  public void sortRankings() {
    playoffMachine.sortRankings();
  }

  @Override
  public List<Team> getRankings() {
    return playoffMachine.getRankings();
  }

  @Override
  public List<Team> getStartingRankings() {
    return playoffMachine.getStartingRankings();
  }

  @Override
  public Map<Integer, Set<Matchup>> getMatchups() {
    return playoffMachine.getMatchups();
  }

  @Override
  public Map<String, Set<Matchup>> getMatchupsJson() {
    return playoffMachine.getMatchupsJson();
  }

  @Override
  public void setWinner(Matchup matchup, int winnerTeamId) {
    playoffMachine.setWinner(matchup, winnerTeamId);
  }

  @Override
  public void setWinnerHome(int matchupPeriod, int matchupId) {
    playoffMachine.setWinnerHome(matchupPeriod, matchupId);
  }

  @Override
  public void setWinnerAway(int matchupPeriod, int matchupId) {
    playoffMachine.setWinnerAway(matchupPeriod, matchupId);
  }

  @Override
  public void setWinnerTie(int matchupPeriod, int matchupId) {
    playoffMachine.setWinnerTie(matchupPeriod, matchupId);
  }

  @Override
  public void printRankings() {
    playoffMachine.printRankings();
  }

  @Override
  public List<Map<String, List<String>>> getScheduleComparison() {
    return league.getScheduleComparison();
  }

  @Override
  public List<Map<String, List<String>>> getWeeklyComparison() {
    return league.getWeeklyComparison();
  }

  @Override
  public List<JSONObject> getProjectedScores(String timePeriod, int matchupPeriod, boolean assessInjuries) {
    return league.getProjectedScores(timePeriod, matchupPeriod, assessInjuries);
  }

  @Override
  public List<JSONObject> getProTeamGames(int matchupPeriod) {
    return league.getProTeamGames(matchupPeriod);
  }

  @Override
  public Double getWinPercentage(int homeTeam, int awayTeam, int numGames, int matchupPeriod, boolean assessInjuries) {
    return league.getWinPercentage(homeTeam, awayTeam, numGames, matchupPeriod, assessInjuries);
  }

  @Override
  public List<String> getRosteredPlayerIds() {
    return league.getRosteredPlayerIds();
  }

  @Override
  public void setAllPlayers(Map<String, Player> allPlayers) {
    league.setAllPlayers(allPlayers);
  }

  @Override
  public Map<String, Player> getAllPlayers() {
    return league.getAllPlayers();
  }

  @Override
  public void setDraftPicks(Map<Integer, DraftPick> draftPicks) {
    league.setDraftPicks(draftPicks);
  }

  @Override
  public Map<Integer, DraftPick> getDraftPicks() {
    return league.getDraftPicks();
  }

  @Override
  public List<String> getRemainingMatchupPeriods() {
    return playoffMachine.getRemainingMatchupPeriods();
  }

  @Override
  public void updateMatchupNames() {

      for (Set<Matchup> set : playoffMachine.getMatchups().values()) {
        for (Matchup matchup : set) {
          matchup.setHomeTeamName(league.getTeam(matchup.getHomeTeamId()).getName());
          matchup.setAwayTeamName(league.getTeam(matchup.getAwayTeamId()).getName());
        }
      }

      for (Team team : playoffMachine.getStartingRankings()) {
        team.setName(league.getTeam(team.getTeamId()).getName());
      }
  }

  @Override
  public boolean getIsSorted() {
    return playoffMachine.getIsSorted();
  }

  @Override
  public void resetPlayoffMachine() {
    playoffMachine.resetPlayoffMachine();
  }
}
