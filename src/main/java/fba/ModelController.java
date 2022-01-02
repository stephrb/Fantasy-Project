package fba;

import fba.model.Model;
import fba.model.team.Team;
import fba.utils.Factory;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

  @PostMapping("/teams")
  public void createModel(@RequestBody JSONObject leagueId) {
    model = Factory.createModel(String.valueOf(leagueId.get("leagueId")));
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
}
