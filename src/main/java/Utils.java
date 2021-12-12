import jdk.jshell.execution.Util;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class Utils {
  public static String getInformationString(
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

  public static Map<String, Double> parseStats(JSONObject jsonStats) {
    Map<String, Double> stats = new HashMap<>();
    if (jsonStats != null) {
      Iterator<Object> keys = jsonStats.keySet().iterator();
      while (keys.hasNext()) {
        Object key = keys.next();
        int keyInt = Integer.parseInt(String.valueOf(key));
        Double val = Double.parseDouble(String.valueOf(jsonStats.get(key)));
        stats.put(MapConstants.statsMap.get(keyInt), val);
      }
    }
    return stats;
  }

  //  public static String matchupToDate(int matchupPeriod) {
  //    TemporalAdjuster adjuster = TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY);
  //    LocalDate date = LocalDate.now().with(adjuster);
  //    date = date.minusWeeks(matchupPeriod-1);
  //    Date date = seasonState.
  //    return (date.getDayOfMonth() > 10)
  //        ? String.valueOf(date.getYear()) + date.getMonth().getValue() + date.getDayOfMonth()
  //        : String.valueOf(date.getYear()) + date.getMonth().getValue() + "0" +
  // date.getDayOfMonth();
  //  }

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
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Map<String, List<Boolean[]>> getTeamWeeklySchedules() {
    JSONObject jsonSchedule = Utils.parseString(Utils.getScheduleInformation());
    return null;
  }
}
