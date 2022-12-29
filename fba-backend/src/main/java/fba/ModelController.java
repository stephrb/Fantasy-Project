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
    public void createModel(@RequestBody JSONObject body, HttpServletRequest request) {

        String leagueId = body.get("leagueId").toString();
        if (leagueId.matches("\\d+")) {
            String remoteAddr = request.getRemoteAddr();
            String key = leagueId + "###" + remoteAddr;
            models.put(key, Factory.createModel(leagueId));
        } else throw new IllegalArgumentException();

    }

    @GetMapping("/rankings")
    public List<Team> getPowerRankings(@RequestHeader("leagueId") String leagueId,
                                       HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        if (model == null) return null;
        return model.getPowerRankings();
    }

    @PostMapping("/demo")
    public void createDemo(HttpServletRequest request) {
        models.put("DEMO###1117484973###" + request.getRemoteAddr(), Factory.createDemo());
    }

    @GetMapping("/request")
    public Boolean modelGenerated(@RequestHeader("leagueId") String leagueId,
                                  HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        return model != null;
    }

    @GetMapping("/compareSchedules")
    public List<Map<String, List<String>>> getScheduleComparison(@RequestHeader("leagueId") String leagueId,
                                                                 HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        return model.getScheduleComparison();
    }

    @GetMapping("/compareWeekly")
    public List<Map<String, List<String>>> getWeeklyComparison(@RequestHeader("leagueId") String leagueId,
                                                               HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        return model.getWeeklyComparison();
    }

    @GetMapping("/playoffRankings")
    public List<Team> getPlayoffRankings(@RequestHeader("leagueId") String leagueId,
                                         HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        return model.getRankings();
    }

    @GetMapping("remainingMatchupPeriods")
    public List<String> getRemainingMatchupPeriods(@RequestHeader("leagueId") String leagueId,
                                                   HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        return model.getRemainingMatchupPeriods();
    }

    @GetMapping("allMatchups")
    public List<String> getAllMatchups(@RequestHeader("leagueId") String leagueId,
                                       HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        List<String> allMatchups = new ArrayList<>();
        for (int i = 1; i < (int) Math.ceil(model.getFinalScoringPeriod() / 7) + 1; i++) {
            allMatchups.add(String.valueOf(i));
        }
        return allMatchups;
    }

    @GetMapping("playoffMachineMatchups")
    public Map<String, Set<Matchup>> getPlayoffMachineMatchups(@RequestHeader("leagueId") String leagueId,
                                                               HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        return model.getMatchupsJson();
    }

    @PostMapping("setWinnerHome")
    public void setWinnerHome(@RequestBody JSONObject json,
                              @RequestHeader("leagueId") String leagueId,
                              HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        model.setWinnerHome(
                Integer.parseInt(String.valueOf(json.get("matchupPeriod"))),
                Integer.parseInt(String.valueOf(json.get("matchupId"))));
    }

    @PostMapping("setWinnerAway")
    public void setWinnerAway(@RequestBody JSONObject json,
                              @RequestHeader("leagueId") String leagueId,
                              HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        model.setWinnerAway(
                Integer.parseInt(String.valueOf(json.get("matchupPeriod"))),
                Integer.parseInt(String.valueOf(json.get("matchupId"))));
    }

    @PostMapping("setWinnerTie")
    public void setWinnerTie(@RequestBody JSONObject json,
                             @RequestHeader("leagueId") String leagueId,
                             HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        model.setWinnerTie(
                Integer.parseInt(String.valueOf(json.get("matchupPeriod"))),
                Integer.parseInt(String.valueOf(json.get("matchupId"))));
    }

    @PostMapping("resetPlayoffMachine")
    public void resetPlayoffMachine(@RequestHeader("leagueId") String leagueId,
                                    HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        model.resetPlayoffMachine();
    }

    @GetMapping("isSorted")
    public boolean isSorted(@RequestHeader("leagueId") String leagueId,
                            HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        return model.getIsSorted();
    }

    @GetMapping("scoreProjections")
    public List<JSONObject> getScoreProjections(
            @RequestParam("timePeriod") String timePeriod,
            @RequestParam("matchupPeriod") int matchupPeriod,
            @RequestParam("assessInjuries") boolean assessInjuries,
            @RequestHeader("leagueId") String leagueId,
            HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        return model.getProjectedScores(timePeriod, matchupPeriod, assessInjuries);
    }

    @GetMapping("currentMatchupPeriod")
    public int getMatchupPeriod(@RequestHeader("leagueId") String leagueId,
                                HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        return model.getCurrentMatchupPeriod();
    }

    @GetMapping("proTeamGames")
    public List<JSONObject> getProTeamGames(@RequestParam("matchupPeriod") int matchupPeriod,
                                            @RequestHeader("leagueId") String leagueId,
                                            HttpServletRequest request) {
        Model model = getModel(leagueId, request);
        return model.getProTeamGames(matchupPeriod);
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    private Model getModel(String leagueId, HttpServletRequest request) {
        String key = leagueId + "###" + request.getRemoteAddr();
        if (leagueId.matches("(DEMO###)?\\d+") && models.containsKey(key)) return models.get(key);
        throw new IllegalArgumentException("Bad id or ip" + leagueId + " " + request.getRemoteAddr());
    }
}
