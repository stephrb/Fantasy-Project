package fba.model;

import fba.model.player.Player;
import fba.model.team.Matchup;
import fba.model.team.Team;
import org.json.simple.JSONObject;

import java.util.*;

public class LeagueImpl implements League {
  private int year;
  private final Map<Integer, Team> teams;
  private final String leagueId;
  private int currentMatchupPeriod;
  private int currentScoringPeriod;
  private int finalScoringPeriod;
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
  public void setFinalScoringPeriod(int finalScoringPeriod) {
    this.finalScoringPeriod = finalScoringPeriod;
  }

  @Override
  public int getFinalScoringPeriod() {
    return finalScoringPeriod;
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

  @Override
  public List<JSONObject> getProjectedScores(String timePeriod, int matchupPeriod, boolean assessInjuries) {
    switch (timePeriod) {
      case "Last_7_2022" -> timePeriod = "Last 7_2022";
      case "Last_15_2022" -> timePeriod = "Last 15_2022";
      case "Last_30_2022" -> timePeriod = "Last 30_2022";
      case "Last_7_2023" -> timePeriod = "Last 7_2023";
      case "Last_15_2023" -> timePeriod = "Last 15_2023";
      case "Last_30_2023" -> timePeriod = "Last 30_2023";
    }

    List<JSONObject> projectedScores = new ArrayList<>();
    for (Team team : getTeams()) {
      projectedScores.add(getTeamProjectedScore(team, timePeriod, matchupPeriod, assessInjuries));
    }
    projectedScores.sort(Comparator.comparingDouble(o -> -Double.parseDouble(String.valueOf(o.get("points")))));
    return projectedScores;
  }

  @Override
  public List<JSONObject> getProTeamGames(int matchupPeriod) {
    List<JSONObject> list = new ArrayList<>();
    for (Map.Entry<String, Map<Integer, boolean[]>> entry : proTeamMatchups.entrySet()) {
      JSONObject jsonGames = new JSONObject();
      String teamName = entry.getKey();
      if (teamName.equals("FA")) continue;
      boolean[] games = entry.getValue().get(matchupPeriod);
      int count=0;
      for (int i = 0; i < 7; i++) if (games[i]) count++;
      jsonGames.put("teamName", teamName);
      jsonGames.put("games", games);
      jsonGames.put("count", count);
      list.add(jsonGames);
    }
    list.sort(Comparator.comparingInt(o -> -Integer.parseInt(String.valueOf(o.get("count")))));
    return list;
  }

  private JSONObject getTeamProjectedScore(Team team, String timePeriod, int matchupPeriod, boolean assessInjuries) {
    double points = team.getPointsFor(matchupPeriod);
    int totalGames = 0;
    for (Player player : team.getPlayers()) {
      int count = 0;
      boolean[] games = proTeamMatchups.get(player.getProTeam()).get(matchupPeriod);
      if (assessInjuries && player.getInjuryStatus().equals("ACTIVE")) {
        count = getGamesCount(games, matchupPeriod);
      } else if (!assessInjuries) count = getGamesCount(games, matchupPeriod);
      points += (player.getStatsMap().get(timePeriod) == null) ? 0 : player.getStatsMap().get(timePeriod).getAvg().get("FPTS") * count;
      totalGames += count;
    }
    JSONObject jsonProjected = new JSONObject();
    jsonProjected.put("teamName", team.getName());
    jsonProjected.put("totalGames", totalGames);
    jsonProjected.put("points", ((int)(points * 100))/100.00);
    return jsonProjected;
  }

  private int getGamesCount(boolean[] playerGames, int matchupPeriod) {
    int count = 0;
    int start = 0;
    if (matchupPeriod == currentMatchupPeriod) start = currentScoringPeriod % 7;
    else if (matchupPeriod < currentMatchupPeriod) start = 7;
    for (int i = start; i < 7; i++) {
      if (playerGames[i]) count++;
    }
    return count;
  }
}
