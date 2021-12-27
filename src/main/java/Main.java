import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

  public static void main(String[] args) {

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
    Scanner s = new Scanner(System.in);
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

        playoffMachine.setWinner(matchup, s.nextInt());
      }
    }
    s.close();
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
