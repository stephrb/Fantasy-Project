package fba.model;

import fba.model.player.Player;
import org.json.simple.JSONObject;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public interface Model extends League, PlayoffMachine {
    List<Map<String, List<String>>> getScheduleComparison();

    List<Map<String, List<String>>> getWeeklyComparison();

    List<String> getRemainingMatchupPeriods();

    void updateMatchupNames();

    Map<String, Map<DayOfWeek, Boolean>> getDailyLineups(int matchupPeriod, boolean assessInjuries, int numRecentGames, int teamId);

    List<Map<String, String>> getMatchupsWinPercentages(int matchupPeriod, boolean assessInjuries, int numRecentGames, boolean reset);

    void setDailyLineUps(JSONObject dailyLineUps);

    List<String> getMatchupPeriodsLeftWithPlayoffs();
}
