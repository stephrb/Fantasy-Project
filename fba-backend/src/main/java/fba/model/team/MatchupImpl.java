package fba.model.team;

import fba.model.player.Player;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.time.DayOfWeek;
import java.util.Map;

public class MatchupImpl implements Matchup {
    private final double homePoints;
    private final double awayPoints;
    private final int homeTeamId;
    private final int awayTeamId;
    private final int matchupId;
    private final int matchupPeriod;
    private int winnerTeamId;
    private boolean isPlayed;
    private String homeTeamName;
    private String awayTeamName;

    private NormalDistribution homeTeamNormal;

    private NormalDistribution awayTeamNormal;

    private Map<Player, Map<DayOfWeek, Boolean>> homeTeamDailyLineups;

    private Map<Player, Map<DayOfWeek, Boolean>> awayTeamDailyLineups;

    private boolean assessInjuries;

    private int numGames;

    public MatchupImpl(
            int homeTeamId, double homePoints, int awayTeamId, double awayPoints, boolean isPlayed, String homeTeamName, String awayTeamName, int matchupId, int matchupPeriod) {
        this.homeTeamId = homeTeamId;
        this.homePoints = homePoints;
        this.awayTeamId = awayTeamId;
        this.awayPoints = awayPoints;
        if (isBye() || homePoints == awayPoints) winnerTeamId = -1;
        else if (homePoints > awayPoints) winnerTeamId = homeTeamId;
        else winnerTeamId = awayTeamId;
        this.isPlayed = isPlayed;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.matchupId = matchupId;
        this.matchupPeriod = matchupPeriod;
        assessInjuries = true;
        numGames = 82;
    }

    @Override
    public int getHomeTeamId() {
        return homeTeamId;
    }

    @Override
    public int getAwayTeamId() {
        return awayTeamId;
    }

    @Override
    public double getAwayPoints() {
        return awayPoints;
    }

    @Override
    public double getHomePoints() {
        return homePoints;
    }

    @Override
    public boolean isBye() {
        return awayPoints == -1;
    }

    @Override
    public int getWinnerTeamId() {
        return winnerTeamId;
    }

    @Override
    public void setWinnerTeamId(int winnerTeamId) {
        if (this.winnerTeamId == winnerTeamId && isPlayed) return;
        isPlayed = true;
        this.winnerTeamId = winnerTeamId;
    }

    @Override
    public boolean getIsPlayed() {
        return isPlayed;
    }

    @Override
    public void setIsPlayed(boolean isPlayed) {
        this.isPlayed = isPlayed;
    }

    @Override
    public boolean isTie() {
        return isPlayed && !isBye() && winnerTeamId == -1;
    }

    @Override
    public String getWinnerTeamName() {
        if (winnerTeamId == homeTeamId) return homeTeamName;
        if (winnerTeamId == awayTeamId) return awayTeamName;
        return null;
    }

    @Override
    public String getHomeTeamName() {
        return homeTeamName;
    }

    @Override
    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    @Override
    public String getAwayTeamName() {
        return awayTeamName;
    }

    @Override
    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    @Override
    public void setWinnerHome() {
        this.winnerTeamId = homeTeamId;
    }

    @Override
    public void setWinnerAway() {
        if (awayTeamId == -1) throw new IllegalStateException();
        this.winnerTeamId = awayTeamId;
    }

    @Override
    public int getMatchupId() {
        return matchupId;
    }

    @Override
    public int getMatchupPeriod() {
        return matchupPeriod;
    }

    @Override
    public Matchup copy() {
        return new MatchupImpl(homeTeamId, homePoints, awayTeamId, awayPoints, isPlayed, homeTeamName, awayTeamName, matchupId, matchupPeriod);
    }

    @Override
    public double getHomeTeamWinPercentage(double x) {
        double htm = 0, atm = 0, htv = 0, atv = 0;
        if (homeTeamNormal != null) {
            htm = homeTeamNormal.getMean();
            htv = homeTeamNormal.getNumericalVariance();
        }
        if (awayTeamNormal != null) {
            atm = awayTeamNormal.getMean();
            atv = awayTeamNormal.getNumericalVariance();
        }
        NormalDistribution normalDistribution = new NormalDistribution(htm - atm, Math.sqrt(htv+atv));
        return 1 - normalDistribution.cumulativeProbability(x);
    }


    public NormalDistribution getHomeTeamNormal() {
        return homeTeamNormal;
    }

    public void setHomeTeamNormal(NormalDistribution homeTeamNormal) {
        this.homeTeamNormal = homeTeamNormal;
    }

    public NormalDistribution getAwayTeamNormal() {
        return awayTeamNormal;
    }

    public void setAwayTeamNormal(NormalDistribution awayTeamNormal) {
        this.awayTeamNormal = awayTeamNormal;
    }

    public Map<Player, Map<DayOfWeek, Boolean>> getHomeTeamDailyLineups() {
        return homeTeamDailyLineups;
    }

    public void setHomeTeamDailyLineups(Map<Player, Map<DayOfWeek, Boolean>> homeTeamDailyLineups) {
        this.homeTeamDailyLineups = homeTeamDailyLineups;
    }

    public Map<Player, Map<DayOfWeek, Boolean>> getAwayTeamDailyLineups() {
        return awayTeamDailyLineups;
    }

    public void setAwayTeamDailyLineups(Map<Player, Map<DayOfWeek, Boolean>> awayTeamDailyLineups) {
        this.awayTeamDailyLineups = awayTeamDailyLineups;
    }

    public int getNumGames() {
        return numGames;
    }

    public void setNumGames(int numGames) {
        this.numGames = numGames;
    }

    @Override
    public boolean getAssessInjuries() {
        return assessInjuries;
    }

    public void setAssessInjuries(boolean assessInjuries) {
        this.assessInjuries = assessInjuries;
    }
}
