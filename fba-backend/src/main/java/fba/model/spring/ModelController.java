package fba.model.spring;

import fba.model.Model;
import fba.model.team.Matchup;
import fba.model.team.Team;
import fba.utils.Factory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.Filter;
import java.sql.Connection;
import java.time.DayOfWeek;
import java.util.*;

@CrossOrigin(origins = {"https://fa.up.railway.app/", "http://localhost:3000", "fba-frontend-production.up.railway.app", "https://fba-frontend-production.up.railway.app"})
@RestController
@RequestMapping("/api/v1/")
public class ModelController {
    private final Map<String, Model> models = new HashMap<>();
    @Autowired
    private Connection connection;

    @Bean
    public Filter requestLoggingFilter() {
        return new RequestLoggingFilter(connection);
    }

    @PostMapping("/create")
    public void createModel(@RequestBody JSONObject body, @RequestHeader("userId") String userId) {

        String leagueId = body.get("leagueId").toString();
        if (leagueId.matches("\\d+")) {
            String key = leagueId + "###" + userId;
            models.put(key, Factory.createModel(leagueId));
        }
        else if (leagueId.matches("(DEMO###)?\\d+")) {
            models.put("DEMO###1117484973###" + userId, Factory.createDemo());
        }else throw new IllegalArgumentException();

    }

    @GetMapping("/rankings")
    public List<Team> getPowerRankings(@RequestHeader("leagueId") String leagueId, @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        if (model == null) return null;
        return model.getPowerRankings();
    }

    @PostMapping("/demo")
    public void createDemo(@RequestHeader("userId") String userId) {
        models.put("DEMO###1117484973###" + userId, Factory.createDemo());
    }

    @GetMapping("/request")
    public Boolean modelGenerated(@RequestHeader("leagueId") String leagueId, @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model != null;
    }

    @GetMapping("/compareSchedules")
    public List<Map<String, List<String>>> getScheduleComparison(@RequestHeader("leagueId") String leagueId, @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getScheduleComparison();
    }

    @GetMapping("/compareWeekly")
    public List<Map<String, List<String>>> getWeeklyComparison(@RequestHeader("leagueId") String leagueId, @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getWeeklyComparison();
    }

    @GetMapping("/playoffRankings")
    public List<Team> getPlayoffRankings(@RequestHeader("leagueId") String leagueId, @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getRankings();
    }

    @GetMapping("remainingMatchupPeriods")
    public List<String> getRemainingMatchupPeriods(@RequestHeader("leagueId") String leagueId, @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getRemainingMatchupPeriods();
    }

    @GetMapping("allMatchups")
    public List<String> getAllMatchups(@RequestHeader("leagueId") String leagueId, @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        List<String> allMatchups = new ArrayList<>();
        for (int i = 1; i < (int) Math.ceil(model.getFinalScoringPeriod() / 7) + 1; i++) {
            allMatchups.add(String.valueOf(i));
        }
        return allMatchups;
    }

    @GetMapping("playoffMachineMatchups")
    public Map<String, Set<Matchup>> getPlayoffMachineMatchups(@RequestHeader("leagueId") String leagueId, @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getMatchupsJson();
    }

    @PostMapping("setWinnerHome")
    public void setWinnerHome(@RequestBody JSONObject json,
                              @RequestHeader("leagueId") String leagueId,
                              @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        model.setWinnerHome(Integer.parseInt(String.valueOf(json.get("matchupPeriod"))), Integer.parseInt(String.valueOf(json.get("matchupId"))));
    }

    @PostMapping("setWinnerAway")
    public void setWinnerAway(@RequestBody JSONObject json,
                              @RequestHeader("leagueId") String leagueId,
                              @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        model.setWinnerAway(Integer.parseInt(String.valueOf(json.get("matchupPeriod"))), Integer.parseInt(String.valueOf(json.get("matchupId"))));
    }

    @PostMapping("setWinnerTie")
    public void setWinnerTie(@RequestBody JSONObject json,
                             @RequestHeader("leagueId") String leagueId,
                             @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        model.setWinnerTie(Integer.parseInt(String.valueOf(json.get("matchupPeriod"))), Integer.parseInt(String.valueOf(json.get("matchupId"))));
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
    public List<JSONObject> getScoreProjections(@RequestParam("timePeriod") String timePeriod,
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

    @PostMapping("getDailyLineups")
    public Map<String, Map<DayOfWeek, Boolean>> getDailyLineups(@RequestBody JSONObject body,
                                                               @RequestHeader("leagueId") String leagueId,
                                                               @RequestHeader("userId") String userId) {
        int matchupPeriod = Integer.parseInt(body.get("matchupPeriod").toString());
        boolean assessInjuries = Boolean.parseBoolean(body.get("assessInjuries").toString());
        int numRecentGames = Integer.parseInt(body.get("numRecentGames").toString());
        int teamId = Integer.parseInt(body.get("teamId").toString());

        Model model = getModel(leagueId, userId);
        return model.getDailyLineups(matchupPeriod, assessInjuries, numRecentGames, teamId);
    }

    @PostMapping("setDailyLineups")
    public void setDailyLineups(@RequestBody JSONObject body,
                                @RequestHeader("leagueId") String leagueId,
                                @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        model.setDailyLineUps(body);

    }

    @GetMapping("matchupsWinPercentages")
    public List<Map<String, String>> matchupWinPercentages(@RequestParam("matchupPeriod") int matchupPeriod,
                                                           @RequestParam("assessInjuries") boolean assessInjuries,
                                                           @RequestParam("numGames") int numGames,
                                                           @RequestParam("reset") boolean reset,
                                                           @RequestHeader("leagueId") String leagueId,
                                                           @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getMatchupsWinPercentages(matchupPeriod, assessInjuries, numGames, reset);
    }

    @GetMapping("matchupsLeftWithPlayoffs")
    public List<String> getMatchupsLeftWithPlayoffs(@RequestHeader("leagueId") String leagueId,
                                                    @RequestHeader("userId") String userId) {
        Model model = getModel(leagueId, userId);
        return model.getMatchupPeriodsLeftWithPlayoffs();
    }
    @GetMapping("/error")
    public String error() {
        return "error";
    }

    private Model getModel(String leagueId, String userId) {
        String key = leagueId + "###" + userId;
        if (leagueId.matches("(DEMO###)?\\d+") && models.containsKey(key)) return models.get(key);
        throw new IllegalArgumentException("LEAGUE OR USER ID NOT FOUND: (" + leagueId + ", " + userId + ")");
    }
}
