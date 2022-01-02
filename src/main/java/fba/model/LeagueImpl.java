package fba.model;

import fba.model.player.Player;
import fba.model.team.Matchup;
import fba.model.team.Team;

import java.util.*;

public class LeagueImpl implements League {
  private int year;
  private final Map<Integer, Team> teams;
  private final String leagueId;
  private int currentMatchupPeriod;
  private int currentScoringPeriod;
  private final String name;
  private List<Player> freeAgents;
  private Map<String, Map<Integer, boolean[]>> proTeamMatchups;

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
  public Team getTeam(int teamId) {
    if (!teams.containsKey(teamId))
      throw new IllegalArgumentException("Model.Team.Team ID not found");
    return teams.get(teamId);
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
  public void addPlayer(Player player, int teamId) {
    teams.get(teamId).addPlayer(player);
  }

  public void setFreeAgents(List<Player> freeAgents) {
    this.freeAgents = freeAgents;
  }

  @Override
  public List<Player> getFreeAgents() {
    return freeAgents;
  }

  @Override
  public void addMatchup(int matchupPeriod, Matchup matchup) {
    teams.get(matchup.getHomeTeamId()).addMatchup(matchupPeriod, matchup);
    if (matchup.getAwayTeamId() != -1)
      teams.get(matchup.getAwayTeamId()).addMatchup(matchupPeriod, matchup);
  }

  @Override
  public int[] compareSchedules(int compareTeamId, int scheduleTeamId) {
    int[] record = new int[3];
    for (int i = 1; i < currentMatchupPeriod; i++) {
      double pointsFor = teams.get(compareTeamId).getPointsFor(i);
      double pointsAgainst = teams.get(scheduleTeamId).getPointsAgainst(i);
      if (pointsFor == pointsAgainst) {
        pointsAgainst = teams.get(compareTeamId).getPointsAgainst(i);
      }
      if (pointsAgainst == -1) continue;
      if (pointsFor > pointsAgainst) record[0]++;
      else if (pointsFor < pointsAgainst) record[1]++;
      else record[2]++;
    }
    return record;
  }

  @Override
  public int[] weeklyRecord(int matchupPeriod, int teamId) {
    int[] record = new int[3];
    double pointsFor = teams.get(teamId).getPointsFor(matchupPeriod);
    for (Team team : getTeams()) {
      if (team.getTeamId() == teamId) continue;
      double pointsAgainst = teams.get(team.getTeamId()).getPointsFor(matchupPeriod);
      if (pointsFor > pointsAgainst) record[0]++;
      else if (pointsFor < pointsAgainst) record[1]++;
      else record[2]++;
    }
    return record;
  }

  @Override
  public Map<Integer, Double> getMedianPointsPerWeek() {
    Map<Integer, Double> medians = new HashMap<>(currentMatchupPeriod);
    for (int i = 1; i < currentMatchupPeriod; i++) {
      List<Double> scores = new ArrayList<>(teams.size());
      for (Team team : teams.values()) {
        scores.add(team.getPointsFor(i));
      }
      scores.sort((o1, o2) -> (int) (o2 - o1));
      double median =
          (scores.size() % 2 == 1)
              ? scores.get(scores.size() / 2)
              : (scores.get(scores.size() / 2) + scores.get(scores.size() / 2 - 1)) / 2;
      medians.put(i, median);
    }
    return medians;
  }

  @Override
  public Double getMedianPointsPerWeek(int matchupPeriod) {
    List<Double> scores = new ArrayList<>(teams.size());
    for (Team team : teams.values()) {
      scores.add(team.getPointsFor(matchupPeriod));
    }
    scores.sort((o1, o2) -> (int) (o2 - o1));
    return (scores.size() % 2 == 1)
        ? scores.get(scores.size() / 2)
        : (scores.get(scores.size() / 2) + scores.get(scores.size() / 2 - 1)) / 2;
  }

  @Override
  public double getPowerRankingScore(int teamId) {
    Team team = teams.get(teamId);
    double pointsVsMedian = 0;
    for (int i = 1; i < currentMatchupPeriod; i++) {
      pointsVsMedian += team.getPointsAgainst(i) - getMedianPointsPerWeek(i);
    }
    return team.getPointsFor()
        + (team.getPointsFor() * team.getWinPercentage())
        + team.getPointsFor()
        + pointsVsMedian;
  }

  @Override
  public List<Team> getPowerRankings() {
    List<Team> teams = new ArrayList<>(getTeams());
    teams.sort((o1, o2) -> (int) (o2.getPowerRankingScore() - o1.getPowerRankingScore()));
    return teams;
  }

  public Map<String, Map<Integer, boolean[]>> getProTeamMatchups() {
    return proTeamMatchups;
  }

  public void setProTeamMatchups(Map<String, Map<Integer, boolean[]>> proTeamMatchups) {
    this.proTeamMatchups = proTeamMatchups;
  }

  @Override
  public List<Map<String, List<String>>> getScheduleComparison() {
    List<Map<String, List<String>>> scheduleComparison = new ArrayList<>();
    for (Team compareTeam : getTeams()) {
      Map<String, List<String>> teamMap = new HashMap<>();
      teamMap.put("teamName", new ArrayList<>());
      teamMap.get("teamName").add(compareTeam.getName());
      teamMap.put("records", new ArrayList<>());
      for (Team scheduleTeam : getTeams()) {
        int[] record = compareSchedules(compareTeam.getTeamId(), scheduleTeam.getTeamId());
        String recordString = record[0] + "-" + record[1] + "-" + record[2];
        teamMap.get("records").add(recordString);
      }
      scheduleComparison.add(teamMap);
    }
    return scheduleComparison;
  }

  @Override
  public List<Map<String, List<String>>> getWeeklyComparison() {
    List<Map<String, List<String>>> weeklyComparison = new ArrayList<>();
    for (Team team : getTeams()) {
      Map<String, List<String>> teamMap = new HashMap<>();
      teamMap.put("teamName", new ArrayList<>());
      teamMap.get("teamName").add(team.getName());
      teamMap.put("records", new ArrayList<>());
      int wins = 0;
      int losses = 0;
      int ties = 0;
      for (int i = 1; i < getCurrentMatchupPeriod(); i++) {
        int[] record = weeklyRecord(i, team.getTeamId());
        String recordString = record[0] + "-" + record[1] + "-" + record[2];
        teamMap.get("records").add(recordString);
        wins += record[0];
        losses += record[1];
        ties += record[2];
      }
      teamMap.get("records").add(wins + "-" + losses + "-" +  ties);
      weeklyComparison.add(teamMap);
    }
    return weeklyComparison;
  }
}
