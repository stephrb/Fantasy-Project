package fba.model.proteams;

import fba.utils.MapConstants;
import fba.utils.Request;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class ProTeamSchedules {
    private final static Map<String, Map<Integer, ProTeamGame>> proTeamMatchups = new HashMap<>();
    private final static Date firstGameDate, lastGameDate, startDate;

    static {
        String year = "2023";
        String requestInfo = Request.get("", year, "?view=proTeamSchedules_wl", "");
        JSONObject jsonSettings = (JSONObject) Request.parseString(requestInfo).get("settings");
        JSONObject jsonOwnership = (JSONObject) jsonSettings.get("playerOwnershipSettings");
        firstGameDate = new Date(Long.parseLong(jsonOwnership.get("firstGameDate").toString()));
        lastGameDate = new Date(Long.parseLong(jsonOwnership.get("lastGameDate").toString()));
        startDate = new Date(Long.parseLong(jsonOwnership.get("startDate").toString()));

        JSONArray jsonProTeams = (JSONArray) jsonSettings.get("proTeams");

        for (Object proTeam : jsonProTeams) {
            JSONObject jsonProTeam = (JSONObject) proTeam;
            String name = jsonProTeam.get("name").toString();
            String location = jsonProTeam.get("location").toString(), abbrev = jsonProTeam.get("abbrev").toString();
            int id = ((Long) jsonProTeam.get("id")).intValue();

            MapConstants.set(abbrev, location, name, id);

            JSONObject jsonGamesByScoringPeriod = (JSONObject) jsonProTeam.get("proGamesByScoringPeriod");
            Map<Integer, ProTeamGame> teamGameMap = new HashMap<>();

            for (Object o : jsonGamesByScoringPeriod.keySet()) {
                String scoringPeriod = (String) o;
                JSONObject game = (JSONObject) ((JSONArray) jsonGamesByScoringPeriod.get(scoringPeriod)).get(0);
                int awayProTeamId = Integer.parseInt(game.get("awayProTeamId").toString()), homeProTeamId = Integer.parseInt(game.get("homeProTeamId").toString()), scoringPeriodId = Integer.parseInt(game.get("scoringPeriodId").toString());
                boolean statsOfficial = Boolean.getBoolean(game.get("statsOfficial").toString());
                Date date = new Date((Long) game.get("date"));
                teamGameMap.put(Integer.parseInt(scoringPeriod), new ProTeamGameImpl(awayProTeamId, homeProTeamId, scoringPeriodId, statsOfficial, date));
            }
            proTeamMatchups.put(abbrev, teamGameMap);
        }
    }

    public static Map<String, Map<Integer, ProTeamGame>> getProTeamMatchups() {
        return proTeamMatchups;
    }

}
