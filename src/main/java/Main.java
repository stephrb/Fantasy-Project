import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

  public static void main(String[] args) {

    String info = Utils.getESPNInformation("1213148421", "2022", "", "");
    // jsonTest(json);
    JSONObject jsonLeague = Utils.parseString(info);
    League league = Factory.createLeague(jsonLeague);
    Factory.createTeams(league, jsonLeague);
  }

  public static void jsonTest(String informationString) {

    JSONParser parser = new JSONParser();

    if (informationString != null)
      try {
        JSONObject test = (JSONObject) parser.parse(informationString);
        JSONArray teams = (JSONArray) test.get("teams");
        for (Object o : teams) {
          JSONObject team = (JSONObject) o;
          System.out.println(
              team.get("location")
                  + " "
                  + team.get("nickname")
                  + " "
                  + ((JSONArray) team.get("owners")).get(0));
        }

      } catch (ParseException e) {
        System.out.print("Parsing error");
      }
  }
}
