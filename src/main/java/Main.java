import Model.*;
import Model.Team.*;
import Utils.*;
import org.json.simple.JSONObject;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import Model.*;

public class Main {

  public static void main(String[] args) {
    //Application.launch(AppLauncher.class);
    String leagueInfo = Request.getESPNInformation("1213148421", "2022", "", "");
    JSONObject jsonLeague = Request.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);

    String teamInfo = Request.getESPNInformation("1213148421", "2022", "?view=mTeam", "");
    JSONObject jsonTeam = Request.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String rostersInfo = Request.getESPNInformation("1213148421", "2022", "?view=mRoster", "");
    JSONObject jsonRosters = Request.parseString(rostersInfo);
    Factory.setRosters(league, jsonRosters);

    String matchupInfo = Request.getESPNInformation("1213148421", "2022", "?view=mBoxscore", "");
    JSONObject jsonMatchups = Request.parseString(matchupInfo);
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
}
