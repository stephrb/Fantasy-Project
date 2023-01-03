package fba.model.team;

import fba.model.player.Player;
import fba.model.proteams.ProTeamSchedules;

import java.time.DayOfWeek;
import java.util.*;

public class TeamImpl implements Team {

    private final String abbrev;
    private final int teamId; // The playerId of the espn user
    private final double pointsFor; // Total points for
    private final double pointsAgainst; // Total points against
    private final double gamesBack; // Number of games back from 1st
    private final int moveToActive;
    private final int drops;
    private final int totalAcquisitions;
    private List<Player> players;
    private String nickname;
    private String location;
    private int wins; // Number of wins
    private int losses; // Number of losses
    private int ties;
    private final Map<Integer, Integer>
            matchUpAcquisitionsPerWeek; // Keys are matchup weeks and values are number of acquisitions
    private final int playoffSeed;
    private final int divisionId;
    private Map<Integer, Matchup> matchups;
    private double powerRankingScore;


    public TeamImpl(
            String nickname,
            String location,
            String abbrev,
            int teamId,
            int wins,
            int losses,
            int ties,
            double pointsFor,
            double pointsAgainst,
            double gamesBack,
            Map<Integer, Integer> matchUpAcquisitionsPerWeek,
            int moveToActive,
            int drops,
            int playoffSeed,
            int totalAcquisitions,
            int divisionId) {
        this.nickname = nickname;
        this.location = location;
        this.abbrev = abbrev;
        this.teamId = teamId;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
        this.pointsFor = pointsFor;
        this.pointsAgainst = pointsAgainst;
        this.gamesBack = gamesBack;
        this.matchUpAcquisitionsPerWeek = matchUpAcquisitionsPerWeek;
        this.moveToActive = moveToActive;
        this.drops = drops;
        this.playoffSeed = playoffSeed;
        this.totalAcquisitions = totalAcquisitions;
        this.divisionId = divisionId;
        players = new ArrayList<>();
        matchups = new HashMap<>();
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
    }

    public int getTeamId() {
        return teamId;
    }

    public String getName() {
        return location + " " + nickname;
    }

    @Override
    public void setName(String name) {
        location = name;
        nickname = "";
    }

    @Override
    public void addMatchup(int matchupPeriod, Matchup matchup) {
        matchups.put(matchupPeriod, matchup);
    }

    @Override
    public Map<Integer, Matchup> getMatchups() {
        return matchups;
    }

    @Override
    public void setMatchups(Map<Integer, Matchup> matchups) {
        this.matchups = matchups;
    }

    @Override
    public double getPointsFor(int matchupPeriod) {
        Matchup matchup = matchups.get(matchupPeriod);
        if (matchup == null)
            return 0;
        //throw new IllegalArgumentException("Model.Model.Team.Team.Matchup not found");
        if (teamId == matchup.getHomeTeamId()) return matchup.getHomePoints();
        else return matchup.getAwayPoints();
    }

    @Override
    public double getPointsAgainst(int matchupPeriod) {
        Matchup matchup = matchups.get(matchupPeriod);
        if (teamId == matchup.getHomeTeamId()) return matchup.getAwayPoints();
        else return matchup.getHomePoints();
    }

    @Override
    public Map<Integer, Double> getPointsForPerWeek(int currentMatchupPeriod) {
        Map<Integer, Double> weeklyPointsFor = new HashMap<>();
        for (int i = 1; i < currentMatchupPeriod; i++) {
            weeklyPointsFor.put(i, getPointsFor(i));
        }
        return weeklyPointsFor;
    }

    @Override
    public Map<Integer, Double> getPointsAgainstPerWeek(int currentMatchupPeriod) {
        Map<Integer, Double> weeklyPointsAgainst = new HashMap<>();
        for (int i = 1; i < currentMatchupPeriod; i++) {
            weeklyPointsAgainst.put(i, getPointsAgainst(i));
        }
        return weeklyPointsAgainst;
    }

    @Override
    public Team copy() {
        Team team =
                new TeamImpl(
                        nickname,
                        location,
                        abbrev,
                        teamId,
                        wins,
                        losses,
                        ties,
                        pointsFor,
                        pointsAgainst,
                        gamesBack,
                        matchUpAcquisitionsPerWeek,
                        moveToActive,
                        drops,
                        playoffSeed,
                        totalAcquisitions,
                        divisionId);
        team.setPlayers(players);
        team.setMatchups(matchups);
        return team;
    }

    @Override
    public int getWins() {
        return wins;
    }

    @Override
    public void setWins(int wins) {
        this.wins = wins;
    }

    @Override
    public int getLosses() {
        return losses;
    }

    @Override
    public void setLosses(int losses) {
        this.losses = losses;
    }

    @Override
    public int getTies() {
        return ties;
    }

    @Override
    public void setTies(int ties) {
        this.ties = ties;
    }

    @Override
    public double getWinPercentage() {
        return ((double) (wins * 2 + ties)) / ((double) (2 * (wins + ties + losses)));
    }

    @Override
    public int getPlayoffSeed() {
        return playoffSeed;
    }

    @Override
    public int getDivisionId() {
        return divisionId;
    }

    @Override
    public int[] getHeadToHeadRecord(int teamId) {
        int[] record = new int[3];

        for (Matchup matchup : matchups.values()) {
            if (matchup.getAwayTeamId() != teamId && matchup.getHomeTeamId() != teamId
                    || !matchup.getIsPlayed()) continue;
            if (matchup.getWinnerTeamId() == this.teamId) record[0]++;
            else if (matchup.getWinnerTeamId() == teamId) record[1]++;
            else record[2]++;
        }
        return record;
    }

    @Override
    public double getPointsFor() {
        return pointsFor;
    }

    @Override
    public double getPowerRankingScore() {
        return powerRankingScore;
    }

    @Override
    public void setPowerRankingScore(double powerRankingScore) {
        this.powerRankingScore = (int) (powerRankingScore * 100) / 100.00;
    }

    @Override
    public String getRecord() {
        return wins + "-" + losses + "-" + ties;
    }

    @Override
    public double getAvgPointsForTeam(String timePeriod) {
        double res = 0;
        for (Player p : players) {
            res += p.getStatsMap().get(timePeriod).getAvg().get("FPTS");
        }
        return res;
    }

    @Override
    public Map<Player, Map<DayOfWeek, Boolean>> getDailyLineups(int matchupPeriod, boolean assessInjuries, int numRecentGames, int currentMatchupPeriod, int currentScoringPeriod) {
        if (matchups.get(matchupPeriod).getHomeTeamId() == teamId && matchups.get(matchupPeriod).getHomeTeamDailyLineups() != null && assessInjuries == matchups.get(matchupPeriod).getAssessInjuries() && matchups.get(matchupPeriod).getNumGames() == numRecentGames)
            return matchups.get(matchupPeriod).getHomeTeamDailyLineups();
        if (matchups.get(matchupPeriod).getAwayTeamId() == teamId && matchups.get(matchupPeriod).getAwayTeamDailyLineups() != null && assessInjuries == matchups.get(matchupPeriod).getAssessInjuries() && matchups.get(matchupPeriod).getNumGames() == numRecentGames)
            return matchups.get(matchupPeriod).getAwayTeamDailyLineups();

        Map<Player, Map<DayOfWeek, Boolean>> dailyLineups = new TreeMap<>(Collections.reverseOrder(Comparator.comparingDouble(p -> p.calculateMean(numRecentGames))));

        int start;
        int end = matchupPeriod * 7;
        if (matchupPeriod == currentMatchupPeriod) start = currentScoringPeriod;
        else if (matchupPeriod > currentMatchupPeriod) start = (matchupPeriod - 1) * 7 + 1;
        else start = end;
        int dayOfWeek = (matchupPeriod == currentMatchupPeriod) ? currentScoringPeriod % 7 : 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }

        if (matchupPeriod > 18) {
            start += 7;
            end += 7;
        }
        
        for (int i = start; i <= end; i++) {
            for (Player p : players) {
                DayOfWeek day = DayOfWeek.of(dayOfWeek);
                dailyLineups.putIfAbsent(p, new HashMap<>());
                if (ProTeamSchedules.getProTeamMatchups().get(p.getProTeam()).get(i) != null) {
                    Boolean playing = !ProTeamSchedules.getProTeamMatchups().get(p.getProTeam()).get(i).hasHappened() && (!assessInjuries || (p.getInjuryStatus().equals("ACTIVE") || p.getInjuryStatus().equals("DAY_TO_DAY")));
                    dailyLineups.get(p).put(day, playing);
                }
            }
            dayOfWeek++;
        }
        for (DayOfWeek d : DayOfWeek.values()) {
            int count = 0;
            for (Map.Entry<Player, Map<DayOfWeek, Boolean>> e : dailyLineups.entrySet()) {
                if (e.getValue().containsKey(d) && e.getValue().get(d)) {
                    count++;
                    if (count > 10) dailyLineups.get(e.getKey()).put(d, false);
                }

            }
        }

        return dailyLineups;
    }
}
