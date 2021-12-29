package model;

import model.Player.Player;
import model.Team.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModelImpl implements Model {
  private League league;
  private PlayoffMachine playoffMachine;

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
  public Map<String, Map<Integer, boolean[]>> getProTeamMatchups() {
    return league.getProTeamMatchups();
  }

  @Override
  public void setProTeamMatchups(Map<String, Map<Integer, boolean[]>> proTeamMatchups) {
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
  public Map<Integer, Set<Matchup>> getMatchups() {
    return playoffMachine.getMatchups();
  }

  @Override
  public void setWinner(Matchup matchup, int winnerTeamId) {
    playoffMachine.setWinner(matchup, winnerTeamId);
  }

  @Override
  public void printRankings() {
    playoffMachine.printRankings();
  }
}
