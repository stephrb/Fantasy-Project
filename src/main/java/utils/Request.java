package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Request {
  /**
   * @param leagueId the ESPN league id
   * @param year the year of the league
   * @param endpoint the endpoint that is being requested
   * @param header header to add to the URL
   * @return the json string gathered from the inputs
   */
  public static String getESPNInformation(
      String leagueId, String year, String endpoint, String header) {

    URL url = null;
    HttpURLConnection conn;
    StringBuilder informationString = null;

    try {
      if (!leagueId.isEmpty())
        url =
            new URL(
                "https://fantasy.espn.com/apis/v3/games/fba/seasons/"
                    + year
                    + "/segments/0/leagues/"
                    + leagueId
                    + endpoint);
      else url = new URL("https://fantasy.espn.com/apis/v3/games/fba/seasons/" + year);
    } catch (Exception e) {
      System.out.print("Bad URL: ");
    }

    try {
      assert url != null;
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      if (!header.isEmpty()) conn.setRequestProperty("x-fantasy-filter", header);
      int responseCode = conn.getResponseCode();
      if (responseCode != HttpURLConnection.HTTP_OK) {
        throw new RuntimeException("Response Code: " + responseCode + url.toString());
      } else {
        informationString = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
          informationString.append(inputLine);
        }

        in.close();
        conn.disconnect();
      }
    } catch (IOException e) {
      System.out.print("Connection error");
    }

    return String.valueOf(informationString);
  }

  /**
   * @param informationString a json information string
   * @return the JSONObject that is parsed from the string
   */
  public static JSONObject parseString(String informationString) {
    JSONParser parser = new JSONParser();
    JSONObject json = null;
    try {
      json = (JSONObject) parser.parse(informationString);
    } catch (ParseException e) {
      System.out.print("Could not parse: " + informationString);
    }
    return json;
  }

  /**
   * @param jsonStats the JSON objet that contains the espn formatted stats of a player
   * @return a map that contains all the stats from the input
   */
  public static Map<String, Double> parseStats(JSONObject jsonStats) {
    Map<String, Double> stats = new HashMap<>();
    if (jsonStats != null) {
      for (Object key : jsonStats.keySet()) {
        int keyInt = Integer.parseInt(String.valueOf(key));
        Double val = Double.parseDouble(String.valueOf(jsonStats.get(key)));
        stats.put(MapConstants.statsMap.get(keyInt), val);
      }
    }
    return stats;
  }

  /**
   * @return the schedule information string from the api at
   *     https://write.corbpie.com/using-the-nba-schedule-api-with-php/
   */
  public static String getScheduleInformation() {
    try {
      URL url = new URL("https://cdn.nba.com/static/json/staticData/scheduleLeagueV2_9.json");
      StringBuilder scheduleInformation;
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      int responseCode = conn.getResponseCode();
      if (responseCode != HttpURLConnection.HTTP_OK) {
        throw new RuntimeException("Response Code: " + responseCode + url.toString());
      } else {
        scheduleInformation = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
          scheduleInformation.append(inputLine);
        }

        in.close();
        conn.disconnect();
        return String.valueOf(scheduleInformation);
      }
    } catch (MalformedURLException e) {
      System.out.println("Bad Url");
    } catch (IOException e) {
      System.out.println("IO exception");
    }
    return null;
  }

  /**
   * @return a map with the key being a pro team and the value being a Map with keys being
   *     matchipPeriods and values being boolean arrays. A value of true means that the pro team has
   *     a game on that day, with an index of 0 being Monday and 6 being Sunday
   */
  public static Map<String, Map<Integer, boolean[]>> getTeamWeeklySchedules() {
    Map<String, Map<Integer, boolean[]>> weeklySchedules = new HashMap<>();
    for (String team : MapConstants.proTeamMap.values()) {
      Map<Integer, boolean[]> teamShedule = new HashMap<>();
      weeklySchedules.put(team, teamShedule);
    }
    JSONObject jsonSchedule = Request.parseString(Request.getScheduleInformation());
    JSONArray jsonGameDates =
        (JSONArray) ((JSONObject) jsonSchedule.get("leagueSchedule")).get("gameDates");
    for (Object jsonGameDate : jsonGameDates) {
      JSONArray jsonGames = (JSONArray) ((JSONObject) jsonGameDate).get("games");
      for (Object game : jsonGames) {
        JSONObject jsonGame = (JSONObject) game;
        if (isRegularSeason(jsonGame)) {

          int day = MapConstants.dayOfWeekMap.get(String.valueOf(jsonGame.get("day")));
          int week = Integer.parseInt(String.valueOf(jsonGame.get("weekNumber")));
          String homeTeam =
              String.valueOf(((JSONObject) jsonGame.get("homeTeam")).get("teamTricode"));
          String awayTeam =
              String.valueOf(((JSONObject) jsonGame.get("awayTeam")).get("teamTricode"));
          try {
            weeklySchedules.get(homeTeam).computeIfAbsent(week, k -> new boolean[7]);
            weeklySchedules.get(homeTeam).get(week)[day] = true;
            weeklySchedules.get(awayTeam).computeIfAbsent(week, k -> new boolean[7]);
            weeklySchedules.get(awayTeam).get(week)[day] = true;
          } catch (Exception e) {
            System.out.println(homeTeam + "\t" + awayTeam + "\t" + week + "\t" + day);
          }
        }
      }
    }
    return weeklySchedules;
  }

  /**
   * @param jsonGame JSON object that has individual game information
   * @return true if the game is in the regular season
   */
  private static boolean isRegularSeason(JSONObject jsonGame) {
    return String.valueOf(jsonGame.get("gameId")).charAt(2) == '2';
  }
}
