package fba;

import fba.model.Model;
import fba.model.team.Matchup;
import fba.model.team.Team;
import fba.utils.Factory;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@CrossOrigin(origins = {"http://localhost:3000", "fba-frontend-production.up.railway.app", "https://fba-frontend-production.up.railway.app"})
@RestController
@RequestMapping("/api/v1/")
public class ModelController {
    private final Map<String, Model> models = new HashMap<>();

    @Bean
    public Filter requestLoggingFilter() {
        for (String m : models.keySet()) {
            System.out.println(m);
        }
        System.out.println("______________");
        return new RequestLoggingFilter();
    }

    @PostMapping("/create")
    public void createModel(@RequestBody JSONObject body, @RequestHeader("userId") String userId) {

        String leagueId = body.get("leagueId").toString();
        if (leagueId.matches("\\d+")) {
            String key = leagueId + "###" + userId;
            models.put(key, Factory.createModel(leagueId));
        } else throw new IllegalArgumentException();

    }

    @GetMapping("/rankings")
    public List<Team> getPowerRankings(@RequestHeader("leagueId") String leagueId,
                                       @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        if (model == null) return null;
        return model.getPowerRankings();
    }

    @PostMapping("/demo")
    public void createDemo(@RequestHeader("userId") String userId) {
        models.put("DEMO###1117484973###" + userId, Factory.createDemo());
    }

    @GetMapping("/request")
    public Boolean modelGenerated(@RequestHeader("leagueId") String leagueId,
                                  @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model != null;
    }

    @GetMapping("/compareSchedules")
    public List<Map<String, List<String>>> getScheduleComparison(@RequestHeader("leagueId") String leagueId,
                                                                 @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getScheduleComparison();
    }

    @GetMapping("/compareWeekly")
    public List<Map<String, List<String>>> getWeeklyComparison(@RequestHeader("leagueId") String leagueId,
                                                               @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getWeeklyComparison();
    }

    @GetMapping("/playoffRankings")
    public List<Team> getPlayoffRankings(@RequestHeader("leagueId") String leagueId,
                                         @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getRankings();
    }

    @GetMapping("remainingMatchupPeriods")
    public List<String> getRemainingMatchupPeriods(@RequestHeader("leagueId") String leagueId,
                                                   @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getRemainingMatchupPeriods();
    }

    @GetMapping("allMatchups")
    public List<String> getAllMatchups(@RequestHeader("leagueId") String leagueId,
                                       @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        List<String> allMatchups = new ArrayList<>();
        for (int i = 1; i < (int) Math.ceil(model.getFinalScoringPeriod() / 7) + 1; i++) {
            allMatchups.add(String.valueOf(i));
        }
        return allMatchups;
    }

    @GetMapping("playoffMachineMatchups")
    public Map<String, Set<Matchup>> getPlayoffMachineMatchups(@RequestHeader("leagueId") String leagueId,
                                                               @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getMatchupsJson();
    }

    @PostMapping("setWinnerHome")
    public void setWinnerHome(@RequestBody JSONObject json,
                              @RequestHeader("leagueId") String leagueId,
                              @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        model.setWinnerHome(
                Integer.parseInt(String.valueOf(json.get("matchupPeriod"))),
                Integer.parseInt(String.valueOf(json.get("matchupId"))));
    }

    @PostMapping("setWinnerAway")
    public void setWinnerAway(@RequestBody JSONObject json,
                              @RequestHeader("leagueId") String leagueId,
                              @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        model.setWinnerAway(
                Integer.parseInt(String.valueOf(json.get("matchupPeriod"))),
                Integer.parseInt(String.valueOf(json.get("matchupId"))));
    }

    @PostMapping("setWinnerTie")
    public void setWinnerTie(@RequestBody JSONObject json,
                             @RequestHeader("leagueId") String leagueId,
                             @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        model.setWinnerTie(
                Integer.parseInt(String.valueOf(json.get("matchupPeriod"))),
                Integer.parseInt(String.valueOf(json.get("matchupId"))));
    }

    @PostMapping("resetPlayoffMachine")
    public void resetPlayoffMachine(@RequestHeader("leagueId") String leagueId,
                                    @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        model.resetPlayoffMachine();
    }

    @GetMapping("isSorted")
    public boolean isSorted(@RequestHeader("leagueId") String leagueId,
                            @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getIsSorted();
    }

    @GetMapping("scoreProjections")
    public List<JSONObject> getScoreProjections(
            @RequestParam("timePeriod") String timePeriod,
            @RequestParam("matchupPeriod") int matchupPeriod,
            @RequestParam("assessInjuries") boolean assessInjuries,
            @RequestHeader("leagueId") String leagueId,
            @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getProjectedScores(timePeriod, matchupPeriod, assessInjuries);
    }

    @GetMapping("currentMatchupPeriod")
    public int getMatchupPeriod(@RequestHeader("leagueId") String leagueId,
                                @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getCurrentMatchupPeriod();
    }

    @GetMapping("proTeamGames")
    public List<JSONObject> getProTeamGames(@RequestParam("matchupPeriod") int matchupPeriod,
                                            @RequestHeader("leagueId") String leagueId,
                                            @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getProTeamGames(matchupPeriod);
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    private Model getModel(String leagueId, String userId) {
        String key = leagueId + "###" + userId;
        System.out.println(Arrays.toString(models.keySet().toArray()));
        if (leagueId.matches("(DEMO###)?\\d+") && models.containsKey(key)) return models.get(key);
        throw new IllegalArgumentException("Bad id or ip" + leagueId + " " + userId);
    }
}
