package fba;

import fba.model.*;
import fba.model.player.Player;
import fba.model.team.Matchup;
import fba.model.team.Team;
import fba.utils.Factory;
import fba.utils.Request;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static org.junit.Assert.*;

public class AppTest {
  @Test
  public void leagueTest() {
    String leagueInfo = Request.get("1213148421", "2022", "", "");
    JSONObject json = Request.parseString(leagueInfo);
    League league = Factory.createLeague(json);
    assertEquals("1213148421", league.getLeagueId());

    assertEquals(2022, league.getYear());
  }

  @Test
  public void addTeamsTest() {
    String leagueInfo = Request.get("1213148421", "2022", "", "");
    JSONObject jsonLeague = Request.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);
    String teamInfo = Request.get("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Request.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    assertNotNull(league.getTeams());
  }

  @Test
  public void setRostersTest() {
    String leagueInfo = Request.get("1213148421", "2022", "", "");
    JSONObject jsonLeague = Request.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);
    String teamInfo = Request.get("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Request.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Request.get("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Request.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    Set<Team> teams = league.getTeams();

    for (Team team : teams) {
      List<Player> players = team.getPlayers();
      System.out.println(team.getName());
      for (Player player : players) {
        assertNotNull(player);
        System.out.println(player.getFullName());
        if (!player.getInjuryStatus().equals("ACTIVE")) {
          System.out.println("------------------is: " + player.getInjuryStatus());
        }
      }
      System.out.println("_________");
    }

    assertNotNull(league);
  }

  @Test
  public void parseStatTest() {
    String rostersInfo = Request.get("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Request.parseString(rostersInfo);
    JSONArray jsonRostersArr = (JSONArray) jsonRosters.get("teams");
    JSONObject jsonTeam = (JSONObject) jsonRostersArr.get(0);
    JSONArray jsonPlayersArr = ((JSONArray) ((JSONObject) jsonTeam.get("roster")).get("entries"));
    JSONObject jsonEntry = (JSONObject) jsonPlayersArr.get(0);
    JSONObject jsonPlayer =
        ((JSONObject) ((JSONObject) jsonEntry.get("playerPoolEntry")).get("player"));
    JSONArray jsonStatsArr = ((JSONArray) jsonPlayer.get("stats"));
    JSONObject jsonStat = ((JSONObject) jsonStatsArr.get(0));
    JSONObject avgStat = ((JSONObject) jsonStat.get("averageStats"));
    JSONObject totStat = ((JSONObject) jsonStat.get("stats"));
    Map<String, Double> avg = Request.parseStats(avgStat);
    Map<String, Double> tot = Request.parseStats(totStat);
    assertNotNull(jsonStat);
  }



  @Test
  public void freeAgentTest() {
    String leagueInfo = Request.get("1213148421", "2022", "", "");
    JSONObject json = Request.parseString(leagueInfo);
    League league = Factory.createLeague(json);
    String header =
        "{\"players\":{\"limit\":1500,\"sortDraftRanks\":{\"sortPriority\":100,\"sortAsc\":true,\"value\":\"STANDARD\"}}}";
    String freeAgentInfo =
        Request.get("1213148421", "2022", "?view=kona_player_info", header);
    JSONObject jsonFreeAgents = Request.parseString(freeAgentInfo);
    JSONArray jsonFreeAgentsArr = (JSONArray) jsonFreeAgents.get("players");
    league.setFreeAgents(Factory.createPlayers(league, jsonFreeAgentsArr).getKey());

    assertNotNull(league.getFreeAgents());
  }

  @Test
  public void connectionTest() {
    JSONObject json = null;
    try {
      URL url =
          new URL(
              "https://fantasy.espn.com/apis/v3/games/fba/seasons/2022/segments/0/leagues/1213148421?view=kona_player_info");

      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      // conn.setRequestProperty("content-type", "application/json");
      String filter =
          "{\"players\":{\"limit\":1500,\"sortDraftRanks\":{\"sortPriority\":100,\"sortAsc\":true,\"value\":\"STANDARD\"}}}";
      JSONObject jsonFilter = Request.parseString(filter);
      conn.setRequestProperty("x-fantasy-filter", String.valueOf(jsonFilter));
      int responseCode = conn.getResponseCode();
      if (responseCode == 200) {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();
        json = Request.parseString(String.valueOf(response));
        // System.out.print(json);
        assertNotNull(json);
      }

    } catch (MalformedURLException e) {
      System.out.println("bad connection");
    } catch (IOException e) {
      System.out.println("io exception");
    }

    assertNotNull(json);
  }

  @Test
  public void dayOfWeekTest() {
    TemporalAdjuster adjuster = TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY);
    LocalDate date = LocalDate.now().with(adjuster);
    for (int i = 0; i < 7; i++) {
      String s =
          (date.getDayOfMonth() > 10)
              ? String.valueOf(date.getYear()) + date.getMonth().getValue() + date.getDayOfMonth()
              : String.valueOf(date.getYear())
                  + date.getMonth().getValue()
                  + "0"
                  + date.getDayOfMonth();
      System.out.println(s);
      date = LocalDate.now().with(adjuster).plusDays(i);
    }
    System.out.println();
  }

  @Test
  public void scheduleTest() {

    JSONObject json = Request.parseString(Request.getScheduleInformation());
    assertNotNull(json);
    Map<String, Map<Integer, boolean[]>> map = Request.getTeamWeeklySchedules();
    assertNotNull(map);
  }

  @Test
  public void pointsGamesTest() {
    String leagueInfo = Request.get("1213148421", "2022", "", "");
    JSONObject jsonLeague = Request.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);
    String teamInfo = Request.get("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Request.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Request.get("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Request.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Request.get("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Request.parseString(matchupInfo);
    Factory.setMatchups(league, jsonMatchups);

    Set<Team> teams = league.getTeams();
    int matchupPeriod = league.getCurrentMatchupPeriod();
    Map<String, Map<Integer, boolean[]>> map = Request.getTeamWeeklySchedules();
    for (Team team : teams) {
      int points = (int) team.getPointsFor(matchupPeriod);
      int totalGames = 0;
      List<Player> players = team.getPlayers();
      for (Player player : players) {
        int count = 0;
        boolean[] games = map.get(player.getProTeam()).get(matchupPeriod);
        if (player.getInjuryStatus().equals("ACTIVE"))
          for (int i = league.getCurrentScoringPeriod() % 7; i < 7; i++) if (games[i]) count++;
        points += player.getStatsMap().get("Last 15  2022").getAvg().get("FPTS") * count;
        totalGames += count;
      }
      System.out.println(
          "Total number of points projected for "
              + team.getName()
              + " in week "
              + matchupPeriod
              + " is "
              + points
              + " in "
              + totalGames
              + " remaining games.");
    }
  }

  @Test
  public void matchupTest() {
    String leagueInfo = Request.get("1213148421", "2022", "", "");
    JSONObject jsonLeague = Request.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);

    String teamInfo = Request.get("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Request.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Request.get("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Request.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Request.get("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Request.parseString(matchupInfo);
    Factory.setMatchups(league, jsonMatchups);
    assertNotNull(league);

    Team team = league.getTeam(7);
    Map<Integer, Double> pointsFor = team.getPointsForPerWeek(league.getCurrentMatchupPeriod());
    Map<Integer, Double> pointsAgainst =
        team.getPointsAgainstPerWeek(league.getCurrentMatchupPeriod());
    assertNotNull(pointsFor);
  }

  @Test
  public void scheduleComparisonTest() {
    String leagueInfo = Request.get("1213148421", "2022", "", "");
    JSONObject jsonLeague = Request.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);

    String teamInfo = Request.get("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Request.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Request.get("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Request.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Request.get("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Request.parseString(matchupInfo);
    Factory.setMatchups(league, jsonMatchups);

    for (Team compareTeam : league.getTeams()) {
      System.out.print(compareTeam.getName() + ":\t");
      for (Team scheduleTeam : league.getTeams()) {
        int[] record = league.compareSchedules(compareTeam.getTeamId(), scheduleTeam.getTeamId());
        System.out.print(
            scheduleTeam.getName() + ": " + record[0] + "-" + record[1] + "-" + record[2] + "\t");
      }
      System.out.println("\n");
    }
    System.out.println("\n\n\n");
    for (int i = 1; i < league.getCurrentMatchupPeriod(); i++) {
      System.out.print(i + "\t\t");
    }
    System.out.print("total");
    System.out.println();
    for (Team team : league.getTeams()) {
      int wins = 0;
      int losses = 0;
      int ties = 0;
      for (int i = 1; i < league.getCurrentMatchupPeriod(); i++) {
        int[] record = league.weeklyRecord(i, team.getTeamId());
        System.out.print(record[0] + "-" + record[1] + "-" + record[2] + "\t");
        wins += record[0];
        losses += record[1];
        ties += record[2];
      }
      System.out.println(wins + "-" + losses + "-" + ties + " for " + team.getName());
    }
    assertNotNull(league);
  }

  @Test
  public void playoffMachineTest() {
    String leagueInfo = Request.get("1213148421", "2022", "", "");
    JSONObject jsonLeague = Request.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);

    String teamInfo = Request.get("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Request.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Request.get("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Request.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Request.get("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Request.parseString(matchupInfo);
    Factory.setMatchups(league, jsonMatchups);

    //    league.getTeam(7).setDivisionId(2);
    //    league.getTeam(3).setLosses(1);
    //    league.getTeam(3).setWins(7);
    PlayoffMachine playoffMachine = new PlayoffMachineImpl(league);
    assertNotNull(playoffMachine);
    int[] record = league.getTeam(7).getHeadToHeadRecord(10);
    // System.out.print(record[0] + "-" + record[1] + "-" + record[2]);

    playoffMachine.setWinner(league.getTeam(7).getMatchups().get(10), -1);
    playoffMachine.printRankings();
    System.out.println("__________________");
    playoffMachine.setWinner(league.getTeam(7).getMatchups().get(10), -1);
    playoffMachine.printRankings();
    System.out.println("__________________");
    playoffMachine.setWinner(league.getTeam(7).getMatchups().get(10), 7);
    playoffMachine.printRankings();
    System.out.println("__________________");
    playoffMachine.setWinner(league.getTeam(7).getMatchups().get(10), -1);
    playoffMachine.printRankings();
    System.out.println("__________________");
    playoffMachine.setWinner(league.getTeam(7).getMatchups().get(10), 3);
    playoffMachine.printRankings();
    System.out.println("__________________");
    assertNotNull(playoffMachine);
  }

  @Test
  public void playoffMachineTest2() {
    String leagueInfo = Request.get("1213148421", "2022", "", "");
    JSONObject jsonLeague = Request.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);

    String teamInfo = Request.get("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Request.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Request.get("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Request.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Request.get("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Request.parseString(matchupInfo);
    Factory.setMatchups(league, jsonMatchups);

    PlayoffMachine playoffMachine = new PlayoffMachineImpl(league);
    for (Map.Entry<Integer, Set<Matchup>> entry : playoffMachine.getMatchups().entrySet()) {
      int matchupPeriod = entry.getKey();
      for (Matchup matchup : entry.getValue()) {
        Team homeTeam = league.getTeam(matchup.getHomeTeamId());
        Team awayTeam = league.getTeam(matchup.getAwayTeamId());
        System.out.println(
            "Outcome of match? "
                + homeTeam.getName()
                + " ("
                + homeTeam.getTeamId()
                + ") "
                + " vs "
                + awayTeam.getName()
                + " ("
                + awayTeam.getTeamId()
                + ") or tie (-1)");

        playoffMachine.setWinner(matchup, -1);
      }
    }
    playoffMachine.sortRankings();
    int rank = 1;
    for (Team team : playoffMachine.getRankings())
      System.out.println(
          rank++
              + "\t"
              + team.getName()
              + "\t"
              + team.getWins()
              + "-"
              + team.getLosses()
              + "-"
              + team.getTies());
  }

  @Test
  public void mediansTest() {
    String leagueInfo = Request.get("1213148421", "2022", "", "");
    JSONObject jsonLeague = Request.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);

    String teamInfo = Request.get("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Request.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Request.get("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Request.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Request.get("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Request.parseString(matchupInfo);
    Factory.setMatchups(league, jsonMatchups);

    Map<Integer, Double> map = league.getMedianPointsPerWeek();
    assertNotNull(map);
  }

  @Test
  public void powerRankingScoreTest() {
    String leagueInfo = Request.get("1213148421", "2022", "", "");
    JSONObject jsonLeague = Request.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);

    String teamInfo = Request.get("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Request.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Request.get("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Request.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Request.get("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Request.parseString(matchupInfo);
    Factory.setMatchups(league, jsonMatchups);

    System.out.println("Power Ranking Scores");
    List<Team> teams = new ArrayList<>();
    for (Team team : league.getTeams()) {
      team.setPowerRankingScore(league.getPowerRankingScore(team.getTeamId()));
      teams.add(team);
    }
    teams.sort((o1, o2) -> (int) (o2.getPowerRankingScore() - o1.getPowerRankingScore()));
    for (Team team : teams)
      System.out.println(
          team.getName()
              + "\t"
              + team.getPowerRankingScore()
              + " "
              + team.getWins()
              + "-"
              + team.getLosses()
              + "-"
              + team.getTies());
  }

  @Test
  public void modelTest() {
    Model model = Factory.createModel("1117484973");
    assertNotNull(model);
  }

  @Test
  public void weeklyComparisonTest() {
    Model model = Factory.createModel("1870103442");
    List<Map<String, List<String>>> list = model.getWeeklyComparison();
    assertNotNull(list);
  }

  @Test
  public void setWinnerTest() {
    Model model = Factory.createModel("1213148421");
    model.printRankings();
    model.setWinnerHome(12, 44);
    model.printRankings();
  }

  @Test
  public void resetPlayoffMachineTest() {
    Model model = Factory.createModel("1213148421");
    model.resetPlayoffMachine();
    assertNotNull(model);
  }

  @Test
  public void getProjectedScoresTest() {
    Model model = Factory.createModel("1117484973");
    List<JSONObject> list = model.getProjectedScores("Season_2023", 4, false);
    assertNotNull(list);
  }

  @Test
  public void getProTeamGamesTest() {
    Model model = Factory.createModel("1213148421");
    List<JSONObject> list = model.getProTeamGames(12);
    assertNotNull(list);
  }

  @Test
  public void demoTest() {
    Model model = Factory.createDemo();
    assertNotNull(model);
  }

  @Test
  public void proTeamsGamesTest() {
    Map<String, Map<Integer, boolean[]>> result = Request.getTeamWeeklySchedules();
    assertNotNull(result);
  }

  @Test
  public void fileReadTest() {
    try {
      Scanner sc = new Scanner(new File("src/main/java/fba/utils/NBA2023Schedule.csv"));
      sc.useDelimiter(","); // sets the delimiter pattern
      while (sc.hasNext()) // returns a boolean value
      {
        System.out.print(sc.next());
      }
      sc.close(); // closes the scanner
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void projectionTest() {
    Model model = Factory.createModel("1117484973");
    model.getProjectedScores("Last_30_2023", 1, true);
  }

  @Test
  public void recentGamesTest() {
    List<String> playerIds = new ArrayList<>();
    playerIds.add("4566434");
    playerIds.add("4065648");
    String playerIdsString = String.join(", ", playerIds);
    int numPrevGames = 10;
    String header =
        "{\"players\":{\"filterIds\":{\"value\":["+playerIdsString+"]},\"filterStatsForTopScoringPeriodIds\":{\"value\":10,\"additionalValue\":[\"002023\",\"102023\",\"002022\",\"012023\",\"022023\",\"032023\",\"042023\"]}}}";
    String recentGames = Request.get("1117484973","2023", "?view=kona_playercard", header);
    JSONObject jsonRecentGames = Request.parseString(recentGames);

    JSONArray jsonPlayers = (JSONArray) jsonRecentGames.get("players");
    Map<String, List<Double>> playerScores = new HashMap<>();
    for (Object player : jsonPlayers) {
      JSONObject jsonPlayer = (JSONObject) player;
      String id = String.valueOf(jsonPlayer.get("id"));
      JSONArray jsonStats = (JSONArray) ((JSONObject) jsonPlayer.get("player")).get("stats");
      List<Double> scores = new ArrayList<>();
      for (Object stat : jsonStats) {
        JSONObject jsonStat = (JSONObject) stat;
        scores.add((Double) jsonStat.get("appliedTotal"));
      }
      playerScores.put(id, scores);
    }
    assertNotNull(playerScores);
  }

  @Test
  public void rosteredPlayerIdsTest() {
    Model model = Factory.createModel("1117484973");
    List<String> rosteredPlayerIds = model.getRosteredPlayerIds();
    assertNotNull(rosteredPlayerIds);
  }

  @Test
  public void varianceCalculator() {
    Model model = Factory.createModel("1117484973");
    Player testPlayer = model.getTeams().iterator().next().getPlayers().iterator().next();
    Pair varmean = testPlayer.calculateVarianceAndMean(model.getCurrentScoringPeriod());
    assertNotNull(varmean);
  }

  @Test
  public void winPercentTest() {
    Model model = Factory.createModel("1117484973");
    int period = model.getCurrentMatchupPeriod();
    for (Matchup m : model.getMatchups().get(period)) {
      int h = m.getAwayTeamId();
      int a = m.getHomeTeamId();
      double wp = model.getWinPercentage(h,a, Integer.MAX_VALUE, period, true);
      System.out.println(model.getTeam(h).getName() + " " + wp + " " + model.getTeam(a).getName() + " " + (1  - wp));
    }
  }

  @Test
  public void varianceTest() {
    TreeSet<Pair<Double, Player>> treeSet = new TreeSet<>(Collections.reverseOrder(Comparator.comparingDouble(Pair::getKey)));
    Model model = Factory.createModel("1117484973");
    for (Team t : model.getTeams()) {
      for (Player p : t.getPlayers()) {
        Pair<Double,Double> vm = p.calculateVarianceAndMean(15);
        treeSet.add(new Pair<>(Math.sqrt(vm.getKey())/vm.getValue(), p));
//        System.out.println(vm.getKey() + " " +vm.getValue());
      }
    }

    for (Pair<Double, Player> p : treeSet) {
      System.out.println(p.getValue().getFullName() + "--- " + (((int)(p.getKey() * 100))) / 100.0);
    }

  }

  @Test
  public void modelSamePlayerTest() {
    Model model = Factory.createModel("1870103442");
    Player p1 = model.getTeams().iterator().next().getPlayers().iterator().next();
    Player p2 = model.getAllPlayers().get(p1.getPlayerId());
    assertSame(p1, p2);
  }

  @Test
  public void draftPickTest() {
    Model model = Factory.createModel("1117484973");
    assertNotNull(model.getDraftPicks());
  }

  @Test
  public void proTeamScheduleTest() {
    Map<String, Map<Integer, ProTeamGame>> proTeamMatchups = ProTeamSchedules.getProTeamMatchups();
    assertNotNull(proTeamMatchups);
  }

  @Test
  public void PMTest() {
    Model model = Factory.createModel("1117484973");
    model.sortRankings();
  }
}
