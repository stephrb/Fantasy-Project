package fba.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class Request {
    /**
     * @param leagueId the ESPN league id
     * @param year     the year of the league
     * @param endpoint the endpoint that is being requested
     * @param header   header to add to the URL
     * @return the json string gathered from the inputs
     */
    public static String get(
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
            else url = new URL("https://fantasy.espn.com/apis/v3/games/fba/seasons/" + year + endpoint);
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
                throw new RuntimeException("Response Code: " + responseCode + url);
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

}
