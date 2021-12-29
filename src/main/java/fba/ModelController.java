package fba;

import fba.model.Model;
import fba.model.ModelImpl;
import fba.model.team.Team;
import fba.utils.Factory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ModelController {
    private Model model;
    @GetMapping("/league")
    public Set<Team> teams (@RequestParam(value = "leagueId", defaultValue = "") String leagueId) {
        if (model == null) model = Factory.createModel(leagueId);
        return model.getTeams();
    }
}
