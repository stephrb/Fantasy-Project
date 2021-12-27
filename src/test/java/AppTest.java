import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    String leagueInfo = Utils.getESPNInformation("1213148421", "2022", "", "");
    JSONObject json = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(json);
    assertEquals("1213148421", league.getLeagueId());

    assertEquals(2022, league.getYear());
  }

  @Test
  public void addTeamsTest() {
    String leagueInfo = Utils.getESPNInformation("1213148421", "2022", "", "");
    JSONObject jsonLeague = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);
    String teamInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Utils.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    assertNotNull(league.getTeams());
  }

  @Test
  public void setRostersTest() {
    String leagueInfo = Utils.getESPNInformation("1213148421", "2022", "", "");
    JSONObject jsonLeague = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);
    String teamInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Utils.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Utils.parseString(rostersInfo);
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
    String rostersInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Utils.parseString(rostersInfo);
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
    Map<String, Double> avg = Utils.parseStats(avgStat);
    Map<String, Double> tot = Utils.parseStats(totStat);
    assertNotNull(jsonStat);
  }

  @Test
  public void testGetKonaPlayerInfo() {
    String header =
        "{\"players\":{\"limit\":1500,\"sortDraftRanks\":{\"sortPriority\":100,\"sortAsc\":true,\"value\":\"STANDARD\"}}}";
    String freeAgentInfo =
        Utils.getESPNInformation("1213148421", "2022", "?view=kona_player_info", header);
    JSONObject jsonFreeAgents = Utils.parseString(freeAgentInfo);
    JSONArray jsonFreeAgentsArr = (JSONArray) jsonFreeAgents.get("players");
    List<Player> fas = Factory.createFreeAgents(jsonFreeAgentsArr);
    assertNotNull(fas);

    for (Player player : fas) {
      assertNotNull(player);
      System.out.println(player.getFullName());
      if (!player.getInjuryStatus().equals("ACTIVE")) {
        System.out.println("------------------is: " + player.getInjuryStatus());
      }
    }
    System.out.println("_________");
  }

  @Test
  public void freeAgentTest() {
    String leagueInfo = Utils.getESPNInformation("1213148421", "2022", "", "");
    JSONObject json = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(json);
    String header =
        "{\"players\":{\"limit\":1500,\"sortDraftRanks\":{\"sortPriority\":100,\"sortAsc\":true,\"value\":\"STANDARD\"}}}";
    String freeAgentInfo =
        Utils.getESPNInformation("1213148421", "2022", "?view=kona_player_info", header);
    JSONObject jsonFreeAgents = Utils.parseString(freeAgentInfo);
    JSONArray jsonFreeAgentsArr = (JSONArray) jsonFreeAgents.get("players");
    league.setFreeAgents(Factory.createFreeAgents(jsonFreeAgentsArr));

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
      JSONObject jsonFilter = Utils.parseString(filter);
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
        json = Utils.parseString(String.valueOf(response));
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

    JSONObject json = Utils.parseString(Utils.getScheduleInformation());
    assertNotNull(json);
    Map<String, Map<Integer, boolean[]>> map = Utils.getTeamWeeklySchedules();
    assertNotNull(map);
  }

  @Test
  public void pointsGamesTest() {
    String leagueInfo = Utils.getESPNInformation("1213148421", "2022", "", "");
    JSONObject jsonLeague = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);
    String teamInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Utils.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Utils.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Utils.parseString(matchupInfo);
    Factory.setMatchups(league, jsonMatchups);

    Set<Team> teams = league.getTeams();
    int matchupPeriod = league.getCurrentMatchupPeriod();
    Map<String, Map<Integer, boolean[]>> map = Utils.getTeamWeeklySchedules();
    for (Team team : teams) {
      int points = (int) team.getPointsFor(matchupPeriod);
      int totalGames = 0;
      List<Player> players = team.getPlayers();
      for (Player player : players) {
        int count = 0;
        boolean[] games = map.get(player.getProTeam()).get(matchupPeriod);
        if (player.getInjuryStatus().equals("ACTIVE"))
          for (int i = league.getCurrentScoringPeriod() % 7; i < 7; i++) if (games[i]) count++;
        points += player.getStatsMap().get("Last 30  2022").getAvg().get("FPTS") * count;
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
              + " remainng games.");
    }
  }

  @Test
  public void matchupTest() {
    String leagueInfo = Utils.getESPNInformation("1213148421", "2022", "", "");
    JSONObject jsonLeague = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);

    String teamInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Utils.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Utils.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Utils.parseString(matchupInfo);
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
    String leagueInfo = Utils.getESPNInformation("1213148421", "2022", "", "");
    JSONObject jsonLeague = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);

    String teamInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Utils.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Utils.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Utils.parseString(matchupInfo);
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
    String leagueInfo = Utils.getESPNInformation("1213148421", "2022", "", "");
    JSONObject jsonLeague = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);

    String teamInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Utils.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Utils.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Utils.parseString(matchupInfo);
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
    String leagueInfo = Utils.getESPNInformation("1213148421", "2022", "", "");
    JSONObject jsonLeague = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);

    String teamInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Utils.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Utils.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Utils.parseString(matchupInfo);
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
    String leagueInfo = Utils.getESPNInformation("1213148421", "2022", "", "");
    JSONObject jsonLeague = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);

    String teamInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Utils.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Utils.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Utils.parseString(matchupInfo);
    Factory.setMatchups(league, jsonMatchups);

    Map<Integer, Double> map = league.getMedianPointsPerWeek();
    assertNotNull(map);
  }

  @Test
  public void powerRankingScoreTest() {
    String leagueInfo = Utils.getESPNInformation("1213148421", "2022", "", "");
    JSONObject jsonLeague = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);

    String teamInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Utils.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Utils.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Utils.getESPNInformation("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Utils.parseString(matchupInfo);
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
}
