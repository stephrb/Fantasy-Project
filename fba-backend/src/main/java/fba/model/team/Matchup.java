package fba.model.team;

import fba.model.player.Player;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.time.DayOfWeek;
import java.util.Map;

public interface Matchup {
    int getHomeTeamId();

    int getAwayTeamId();

    double getAwayPoints();

    double getHomePoints();

    boolean isBye();

    int getWinnerTeamId();

    void setWinnerTeamId(int winnerTeamId);

    boolean getIsPlayed();

    void setIsPlayed(boolean isPlayed);

    boolean isTie();

    String getWinnerTeamName();

    String getHomeTeamName();

    void setHomeTeamName(String homeTeamName);

    String getAwayTeamName();

    void setAwayTeamName(String awayTeamName);

    void setWinnerHome();

    void setWinnerAway();

    int getMatchupId();

    int getMatchupPeriod();

    Matchup copy();

    double getHomeTeamWinPercentage(double x);

    NormalDistribution getHomeTeamNormal();

    void setHomeTeamNormal(NormalDistribution homeTeamNormal);

    NormalDistribution getAwayTeamNormal();

    void setAwayTeamNormal(NormalDistribution awayTeamNormal);

    Map<Player, Map<DayOfWeek, Boolean>> getHomeTeamDailyLineups();

    void setHomeTeamDailyLineups(Map<Player, Map<DayOfWeek, Boolean>> homeTeamDailyLineups);

    Map<Player, Map<DayOfWeek, Boolean>> getAwayTeamDailyLineups();

    void setAwayTeamDailyLineups(Map<Player, Map<DayOfWeek, Boolean>> awayTeamDailyLineups);

    int getNumGames();

    void setNumGames(int numGames);

    boolean getAssessInjuries();

    void setAssessInjuries(boolean assessInjuries);

}
