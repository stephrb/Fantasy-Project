package fba;

import fba.model.Model;
import fba.model.team.Matchup;
import fba.model.team.Team;
import fba.utils.Factory;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class ModelController {
  private Model model;

  @GetMapping("/rankings")
  public List<Team> getPowerRankings() {
    if (model == null) return null;
    return model.getPowerRankings();
  }

  @PostMapping("/create")
  public void createModel(@RequestBody JSONObject leagueId) {
    model = Factory.createModel(String.valueOf(leagueId.get("leagueId")));
  }

  @PostMapping("/demo")
  public void createDemo() {
    model = Factory.createDemo();
  }

  @GetMapping("/request")
  public Boolean modelGenerated() {
    return model != null;
  }

  @GetMapping("/compareSchedules")
  public List<Map<String, List<String>>> getScheduleComparison() {
    return model.getScheduleComparison();
  }

  @GetMapping("/compareWeekly")
  public List<Map<String, List<String>>> getWeeklyComparison() {
    return model.getWeeklyComparison();
  }

  @GetMapping("/playoffRankings")
  public List<Team> getPlayoffRankings() {
    return model.getRankings();
  }

  @GetMapping("remainingMatchupPeriods")
  public List<String> getRemainingMatchupPeriods() {
    return model.getRemainingMatchupPeriods();
  }

  @GetMapping("allMatchups")
  public List<String> getAllMatchups() {
    List<String> allMatchups = new ArrayList<>();
    for (int i = 1; i < (int) Math.ceil(model.getFinalScoringPeriod() / 7) + 1; i++) {
      allMatchups.add(String.valueOf(i));
    }
    return allMatchups;
  }
  @GetMapping("playoffMachineMatchups")
  public Map<String, Set<Matchup>> getPlayoffMachineMatchups() {
    return model.getMatchupsJson();
  }

  @PostMapping("setWinnerHome")
  public void setWinnerHome(@RequestBody JSONObject json) {
    System.out.println(model.getIsSorted());
    model.setWinnerHome(
        Integer.parseInt(String.valueOf(json.get("matchupPeriod"))),
        Integer.parseInt(String.valueOf(json.get("matchupId"))));
  }

  @PostMapping("setWinnerAway")
  public void setWinnerAway(@RequestBody JSONObject json) {
    System.out.println(model.getIsSorted());
    model.setWinnerAway(
        Integer.parseInt(String.valueOf(json.get("matchupPeriod"))),
        Integer.parseInt(String.valueOf(json.get("matchupId"))));
  }

  @PostMapping("setWinnerTie")
  public void setWinnerTie(@RequestBody JSONObject json) {
    model.setWinnerTie(
        Integer.parseInt(String.valueOf(json.get("matchupPeriod"))),
        Integer.parseInt(String.valueOf(json.get("matchupId"))));
  }

  @PostMapping("resetPlayoffMachine")
  public void resetPlayoffMachine() {
    model.resetPlayoffMachine();
  }

  @GetMapping("isSorted")
  public boolean isSorted() {
    return model.getIsSorted();
  }

  @GetMapping("scoreProjections")
  public List<JSONObject> getScoreProjections(
      @RequestParam("timePeriod") String timePeriod,
      @RequestParam("matchupPeriod") int matchupPeriod,
      @RequestParam("assessInjuries") boolean assessInjuries) {
    System.out.print(matchupPeriod);
    return model.getProjectedScores(timePeriod, matchupPeriod, assessInjuries);
  }

  @GetMapping("currentMatchupPeriod")
  public int getMatchupPeriod() {
    return model.getCurrentMatchupPeriod();
  }

  @GetMapping("proTeamGames")
  public List<JSONObject> getProTeamGames(@RequestParam("matchupPeriod") int matchupPeriod) {
    System.out.print(matchupPeriod);
    return model.getProTeamGames(matchupPeriod);
  }
}
