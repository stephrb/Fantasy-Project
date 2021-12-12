import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class AppTest {
  @Test
  public void leagueTest() {
    String leagueInfo = Utils.getInformationString("1213148421", "2022", "", "");
    JSONObject json = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(json);
    assertEquals("1213148421", league.getLeagueId());

    assertEquals(2022, league.getYear());
  }

  @Test
  public void addTeamsTest() {
    String leagueInfo = Utils.getInformationString("1213148421", "2022", "", "");
    JSONObject jsonLeague = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);
    String teamInfo = Utils.getInformationString("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Utils.parseString(teamInfo);
    Factory.createTeams(league, jsonTeam);

    assertNotNull(league.getTeams());
  }

  @Test
  public void setRostersTest() {
    String leagueInfo = Utils.getInformationString("1213148421", "2022", "", "");
    JSONObject jsonLeague = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);
    String teamInfo = Utils.getInformationString("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Utils.parseString(teamInfo);
    Factory.createTeams(league, jsonTeam);

    String rostersInfo = Utils.getInformationString("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Utils.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    Set<Team> teams = league.getTeams();

    for (Team team : teams) {
      List<Player> players = team.getPlayers();
      // System.out.println(team.getName());
      for (Player player : players) {
        assertNotNull(player);
        // System.out.println(player.getFullName());
        if (!player.getInjuryStatus().equals("ACTIVE")) {
          // System.out.println("------------------is: " + player.getInjuryStatus());
        }
      }
      System.out.println("_________");
    }

    assertNotNull(league);
  }

  @Test
  public void parseStatTest() {
    String rostersInfo = Utils.getInformationString("1213148421", "2022", "?view=mRoster", "");
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
        Utils.getInformationString("1213148421", "2022", "?view=kona_player_info", header);
    JSONObject jsonFreeAgents = Utils.parseString(freeAgentInfo);
    JSONArray jsonFreeAgentsArr = (JSONArray) jsonFreeAgents.get("players");
    List<Player> fas = Factory.createFreeAgents(jsonFreeAgentsArr);
    assertNotNull(fas);

    for (Player player : fas) {
      assertNotNull(player);
      // System.out.println(player.getFullName());
      if (!player.getInjuryStatus().equals("ACTIVE")) {
        // System.out.println("------------------is: " + player.getInjuryStatus());
      }
    }
    // System.out.println("_________");
  }

  @Test
  public void freeAgentTest() {
    String leagueInfo = Utils.getInformationString("1213148421", "2022", "", "");
    JSONObject json = Utils.parseString(leagueInfo);
    League league = Factory.createLeague(json);
    String header =
        "{\"players\":{\"limit\":1500,\"sortDraftRanks\":{\"sortPriority\":100,\"sortAsc\":true,\"value\":\"STANDARD\"}}}";
    String freeAgentInfo =
        Utils.getInformationString("1213148421", "2022", "?view=kona_player_info", header);
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
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
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
  }
}
