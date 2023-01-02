package fba.model;

import fba.model.player.Player;
import fba.model.proteams.ProTeamGame;
import fba.model.team.DraftPick;
import fba.model.team.Matchup;
import fba.model.team.Team;
import org.json.simple.JSONObject;

import java.time.DayOfWeek;
import java.util.*;

public class ModelImpl implements Model {
    private final League league;
    private final PlayoffMachine playoffMachine;

    public ModelImpl(League league, PlayoffMachine playoffMachine) {
        this.league = league;
        this.playoffMachine = playoffMachine;
    }

    @Override
    public Set<Team> getTeams() {
        return league.getTeams();
    }

    @Override
    public Team getTeam(int teamId) {
        return league.getTeam(teamId);
    }

    @Override
    public void addTeam(int id, Team team) {
        league.addTeam(id, team);
    }

    @Override
    public int getYear() {
        return league.getYear();
    }

    @Override
    public void setYear(int year) {
        league.setYear(year);
    }

    @Override
    public String getLeagueId() {
        return league.getLeagueId();
    }

    @Override
    public int getCurrentMatchupPeriod() {
        return league.getCurrentMatchupPeriod();
    }

    @Override
    public void setCurrentMatchupPeriod(int currentMatchupPeriod) {
        league.setCurrentMatchupPeriod(currentMatchupPeriod);
    }

    @Override
    public int getCurrentScoringPeriod() {
        return league.getCurrentScoringPeriod();
    }

    @Override
    public void setCurrentScoringPeriod(int currentScoringPeriod) {
        league.setCurrentScoringPeriod(currentScoringPeriod);
    }

    @Override
    public int getFinalScoringPeriod() {
        return league.getFinalScoringPeriod();
    }

    @Override
    public void setFinalScoringPeriod(int finalScoringPeriod) {
        league.setFinalScoringPeriod(finalScoringPeriod);
    }

    @Override
    public void addPlayer(Player player, int teamId) {
        league.addPlayer(player, teamId);
    }

    @Override
    public List<Player> getFreeAgents() {
        return league.getFreeAgents();
    }

    @Override
    public void setFreeAgents(List<Player> freeAgents) {
        league.setFreeAgents(freeAgents);
    }

    @Override
    public void addMatchup(int matchupPeriod, Matchup matchup) {
        league.addMatchup(matchupPeriod, matchup);
    }

    @Override
    public int[] compareSchedules(int compareTeamId, int scheduleTeamId) {
        return league.compareSchedules(compareTeamId, scheduleTeamId);
    }

    @Override
    public int[] weeklyRecord(int matchupPeriod, int teamId) {
        return league.weeklyRecord(matchupPeriod, teamId);
    }

    @Override
    public Map<Integer, Double> getMedianPointsPerWeek() {
        return league.getMedianPointsPerWeek();
    }

    @Override
    public Double getMedianPointsPerWeek(int matchupPeriod) {
        return league.getMedianPointsPerWeek(matchupPeriod);
    }

    @Override
    public double getPowerRankingScore(int teamId) {
        return league.getPowerRankingScore(teamId);
    }

    @Override
    public List<Team> getPowerRankings() {
        return league.getPowerRankings();
    }

    @Override
    public Map<String, Map<Integer, ProTeamGame>> getProTeamMatchups() {
        return league.getProTeamMatchups();
    }

    @Override
    public void setProTeamMatchups(Map<String, Map<Integer, ProTeamGame>> proTeamMatchups) {
        league.setProTeamMatchups(proTeamMatchups);
    }

    @Override
    public void sortRankings() {
        playoffMachine.sortRankings();
    }

    @Override
    public List<Team> getRankings() {
        return playoffMachine.getRankings();
    }

    @Override
    public List<Team> getStartingRankings() {
        return playoffMachine.getStartingRankings();
    }

    @Override
    public Map<Integer, Set<Matchup>> getPlayoffMachineMatchups() {
        return playoffMachine.getPlayoffMachineMatchups();
    }

    @Override
    public Map<String, Set<Matchup>> getMatchupsJson() {
        return playoffMachine.getMatchupsJson();
    }

    @Override
    public void setWinner(Matchup matchup, int winnerTeamId) {
        playoffMachine.setWinner(matchup, winnerTeamId);
    }

    @Override
    public void setWinnerHome(int matchupPeriod, int matchupId) {
        playoffMachine.setWinnerHome(matchupPeriod, matchupId);
    }

    @Override
    public void setWinnerAway(int matchupPeriod, int matchupId) {
        playoffMachine.setWinnerAway(matchupPeriod, matchupId);
    }

    @Override
    public void setWinnerTie(int matchupPeriod, int matchupId) {
        playoffMachine.setWinnerTie(matchupPeriod, matchupId);
    }

    @Override
    public void printRankings() {
        playoffMachine.printRankings();
    }

    @Override
    public List<Map<String, List<String>>> getScheduleComparison() {
        return league.getScheduleComparison();
    }

    @Override
    public List<Map<String, List<String>>> getWeeklyComparison() {
        return league.getWeeklyComparison();
    }

    @Override
    public List<JSONObject> getProjectedScores(String timePeriod, int matchupPeriod, boolean assessInjuries) {
        return league.getProjectedScores(timePeriod, matchupPeriod, assessInjuries);
    }

    @Override
    public List<JSONObject> getProTeamGames(int matchupPeriod) {
        return league.getProTeamGames(matchupPeriod);
    }

    @Override
    public Double getWinPercentage(int homeTeam, int awayTeam, int numGames, int matchupPeriod, boolean assessInjuries, boolean reset) {
        return league.getWinPercentage(homeTeam, awayTeam, numGames, matchupPeriod, assessInjuries, reset);
    }

    @Override
    public Double getWinPercentage(Matchup matchup, int numGames, boolean assessInjuries, boolean reset) {
        return league.getWinPercentage(matchup, numGames, assessInjuries, reset);
    }

    @Override
    public void setDailyLineUps(int teamId, int numGames, int matchupPeriod, Map<Player, Map<DayOfWeek, Boolean>> dailyLineUps, double meanAdj, double varAdj) {
        league.setDailyLineUps(teamId, numGames, matchupPeriod, dailyLineUps, meanAdj, varAdj);
    }

    @Override
    public List<String> getRosteredPlayerIds() {
        return league.getRosteredPlayerIds();
    }

    @Override
    public Map<String, Player> getAllPlayers() {
        return league.getAllPlayers();
    }

    @Override
    public void setAllPlayers(Map<String, Player> allPlayers) {
        league.setAllPlayers(allPlayers);
    }

    @Override
    public Map<Integer, DraftPick> getDraftPicks() {
        return league.getDraftPicks();
    }

    @Override
    public void setDraftPicks(Map<Integer, DraftPick> draftPicks) {
        league.setDraftPicks(draftPicks);
    }

    @Override
    public Map<Integer, Set<Matchup>> getMatchups() {
        return league.getMatchups();
    }


    @Override
    public List<String> getRemainingMatchupPeriods() {
        return playoffMachine.getRemainingMatchupPeriods();
    }

    @Override
    public void updateMatchupNames() {

        for (Set<Matchup> set : playoffMachine.getPlayoffMachineMatchups().values()) {
            for (Matchup matchup : set) {
                matchup.setHomeTeamName(league.getTeam(matchup.getHomeTeamId()).getName());
                matchup.setAwayTeamName(league.getTeam(matchup.getAwayTeamId()).getName());
            }
        }

        for (Team team : playoffMachine.getStartingRankings()) {
            team.setName(league.getTeam(team.getTeamId()).getName());
        }
    }

    @Override
    public Map<String, Map<DayOfWeek, Boolean>> getDailyLineups(int matchupPeriod, boolean assessInjuries, int numRecentGames, int teamId) {
        Map<Player, Map<DayOfWeek, Boolean>> map = league.getTeam(teamId).getDailyLineups(matchupPeriod, assessInjuries, numRecentGames, league.getCurrentMatchupPeriod(), league.getCurrentScoringPeriod());
        Map<String, Map<DayOfWeek, Boolean>> res = new LinkedHashMap<>();
        for (Map.Entry<Player, Map<DayOfWeek, Boolean>> e : map.entrySet()) {
            String key = e.getKey().getPlayerId() + ":" + e.getKey().getFullName();
            res.put(key, e.getValue());
        }
        return res;
    }

    @Override
    public synchronized List<Map<String, String>> getMatchupsWinPercentages(int matchupPeriod, boolean assessInjuries, int numRecentGames, boolean reset) {
        List<Map<String, String>> matchupWinPercentagesJson = new ArrayList<>();
        Set<Matchup> seen = new HashSet<>();
        for (Team t : getTeams()) {
            Matchup matchup = t.getMatchups().get(matchupPeriod);
            if (seen.add(matchup)) {
                Map<String, String> matchupsJson = new HashMap<>();
                double homeTeamWinPercentage = getWinPercentage(matchup, numRecentGames, assessInjuries, reset);
                double awayTeamWinPercentage = 1 - homeTeamWinPercentage;
                String homeTeamId = String.valueOf(matchup.getHomeTeamId());
                String awayTeamId = String.valueOf(matchup.getAwayTeamId());
                String homeTeamName = matchup.getHomeTeamName();
                String awayTeamName = matchup.getAwayTeamName();
                String matchupId = String.valueOf(matchup.getMatchupId());

                matchupsJson.put("homeTeamWinPercentage", String.valueOf(homeTeamWinPercentage));
                matchupsJson.put("awayTeamWinPercentage", String.valueOf(awayTeamWinPercentage));
                matchupsJson.put("homeTeamId", homeTeamId);
                matchupsJson.put("awayTeamId", awayTeamId);
                matchupsJson.put("homeTeamName", homeTeamName);
                matchupsJson.put("awayTeamName", awayTeamName);
                matchupsJson.put("matchupId", matchupId);


                matchupWinPercentagesJson.add(matchupsJson);
            }
        }
        return matchupWinPercentagesJson;
    }

    @Override
    public synchronized void setDailyLineUps(JSONObject jsonObject) {
        /*
        teamId: 1,
        numGames: 10,
        matchupPeriod: 11,
        meanAdj: 100.0,
        varAdj: 150.0,
        dailyLineups:
        {"3112335:Nikola Jokic":{"FRIDAY":true,"SUNDAY":true},
        "3149673:Pascal Siakam":{"FRIDAY":true,"THURSDAY":false},
        "2490149:CJ McCollum":{"FRIDAY":true,"SATURDAY":true}}
         */
        int teamId = Integer.parseInt(jsonObject.get("teamId").toString()),
            numGames = Integer.parseInt(jsonObject.get("numGames").toString()),
            matchupPeriod = Integer.parseInt(jsonObject.get("matchupPeriod").toString());

        double meanAdj = Double.parseDouble(jsonObject.get("meanAdj").toString()),
            varAdj = Double.parseDouble(jsonObject.get("varAdj").toString());
        Map<Player, Map<DayOfWeek, Boolean>> dailyLineUps = new TreeMap<>(Collections.reverseOrder(Comparator.comparingDouble(p -> p.calculateMean(numGames))));
        Map<String, Map<String, Boolean>> dailyLineupsJson = (Map<String, Map<String, Boolean>>) jsonObject.get("dailyLineups");
        for (String key : dailyLineupsJson.keySet()) {
            Player p = getAllPlayers().get(key.split(":")[0]);
            Map<String, Boolean> playerGames = dailyLineupsJson.get(key);
            Map<DayOfWeek, Boolean> dayOfWeekBooleanMap = new HashMap<>();
            for (String d : playerGames.keySet()) {
                DayOfWeek day = DayOfWeek.valueOf(d);
                Boolean playing = playerGames.get(d);
                dayOfWeekBooleanMap.put(day, playing);
            }
            dailyLineUps.put(p, dayOfWeekBooleanMap);

        }
        setDailyLineUps(teamId, numGames, matchupPeriod, dailyLineUps, meanAdj, varAdj);
    }

    @Override
    public List<String> getMatchupPeriodsLeftWithPlayoffs() {
        int start = getCurrentMatchupPeriod();
        int end = 0;
        for (Team t : getTeams()) {
            for (Integer i : t.getMatchups().keySet()) {
                end = Math.max(end, i);
            }
        }
        List<String> matchupPeriodsLeftWithPlayoffs = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            matchupPeriodsLeftWithPlayoffs.add(String.valueOf(i));
        }
        return matchupPeriodsLeftWithPlayoffs;
    }

    @Override
    public boolean getIsSorted() {
        return playoffMachine.getIsSorted();
    }

    @Override
    public void resetPlayoffMachine() {
        playoffMachine.resetPlayoffMachine();
    }
}
