package fba.model;

import java.util.List;
import java.util.Map;

public interface Model extends League, PlayoffMachine {
    List<Map<String, List<String>>> getScheduleComparison();

    List<Map<String, List<String>>> getWeeklyComparison();

    List<String> getRemainingMatchupPeriods();

    void updateMatchupNames();
}
