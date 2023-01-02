package fba.model;

import fba.model.player.Player;
import fba.model.proteams.ProTeamGame;
import fba.model.team.DraftPick;
import fba.model.team.Matchup;
import fba.model.team.Team;
import javafx.util.Pair;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.json.simple.JSONObject;

import java.time.DayOfWeek;
import java.util.*;

public class LeagueImpl implements League {
    private final Map<Integer, Team> teams;
    private final String leagueId;
    private final String name;
    private int year;
    private int currentMatchupPeriod;
    private int currentScoringPeriod;
    private int finalScoringPeriod;
    private List<Player> freeAgents;
    private Map<String, Player> allPlayers;
    private Map<String, Map<Integer, ProTeamGame>> proTeamMatchups;
    private Map<Integer, DraftPick> draftPicks;

    public LeagueImpl(String leagueId, String name) {
        teams = new HashMap<>();
        this.leagueId = leagueId;
        this.name = name;
    }

    @Override
    public Set<Team> getTeams() {
        return new HashSet<>((teams.values()));
    }

    @Override
    public Team getTeam(int teamId) {
        if (!teams.containsKey(teamId))
            throw new IllegalArgumentException("Model.Team.Team ID not found");
        return teams.get(teamId);
    }

    @Override
    public void addTeam(int id, Team team) {
        teams.put(id, team);
    }

    @Override
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String getLeagueId() {
        return leagueId;
    }

    @Override
    public int getCurrentMatchupPeriod() {
        return currentMatchupPeriod;
    }

    @Override
    public void setCurrentMatchupPeriod(int currentMatchupPeriod) {
        this.currentMatchupPeriod = currentMatchupPeriod;
    }

    @Override
    public int getCurrentScoringPeriod() {
        return currentScoringPeriod;
    }

    @Override
    public void setCurrentScoringPeriod(int currentScoringPeriod) {
        this.currentScoringPeriod = currentScoringPeriod;
    }

    @Override
    public int getFinalScoringPeriod() {
        return finalScoringPeriod;
    }

    @Override
    public void setFinalScoringPeriod(int finalScoringPeriod) {
        this.finalScoringPeriod = finalScoringPeriod;
    }

    @Override
    public void addPlayer(Player player, int teamId) {
        teams.get(teamId).addPlayer(player);
    }

    @Override
    public Map<String, Player> getAllPlayers() {
        return allPlayers;
    }

    public void setAllPlayers(Map<String, Player> allPlayers) {
        this.allPlayers = allPlayers;
    }

    @Override
    public Map<Integer, DraftPick> getDraftPicks() {
        return draftPicks;
    }

    @Override
    public void setDraftPicks(Map<Integer, DraftPick> draftPicks) {
        this.draftPicks = draftPicks;
    }

    @Override
    public Map<Integer, Set<Matchup>> getMatchups() {
        return null;
    }

    @Override
    public List<Player> getFreeAgents() {
        return freeAgents;
    }

    public void setFreeAgents(List<Player> freeAgents) {
        this.freeAgents = freeAgents;
    }

    @Override
    public void addMatchup(int matchupPeriod, Matchup matchup) {
        teams.get(matchup.getHomeTeamId()).addMatchup(matchupPeriod, matchup);
        if (matchup.getAwayTeamId() != -1)
            teams.get(matchup.getAwayTeamId()).addMatchup(matchupPeriod, matchup);
    }

    @Override
    public int[] compareSchedules(int compareTeamId, int scheduleTeamId) {
        int[] record = new int[3];
        for (int i = 1; i < currentMatchupPeriod; i++) {
            double pointsFor = teams.get(compareTeamId).getPointsFor(i);
            double pointsAgainst = teams.get(scheduleTeamId).getPointsAgainst(i);
            if (pointsFor == pointsAgainst) {
                pointsAgainst = teams.get(compareTeamId).getPointsAgainst(i);
            }
            if (pointsAgainst == -1) continue;
            if (pointsFor > pointsAgainst) record[0]++;
            else if (pointsFor < pointsAgainst) record[1]++;
            else record[2]++;
        }
        return record;
    }

    @Override
    public int[] weeklyRecord(int matchupPeriod, int teamId) {
        int[] record = new int[3];
        double pointsFor = teams.get(teamId).getPointsFor(matchupPeriod);
        for (Team team : getTeams()) {
            if (team.getTeamId() == teamId) continue;
            double pointsAgainst = teams.get(team.getTeamId()).getPointsFor(matchupPeriod);
            if (pointsFor > pointsAgainst) record[0]++;
            else if (pointsFor < pointsAgainst) record[1]++;
            else record[2]++;
        }
        return record;
    }

    @Override
    public Map<Integer, Double> getMedianPointsPerWeek() {
        Map<Integer, Double> medians = new HashMap<>(currentMatchupPeriod);
        for (int i = 1; i < currentMatchupPeriod; i++) {
            List<Double> scores = new ArrayList<>(teams.size());
            for (Team team : teams.values()) {
                scores.add(team.getPointsFor(i));
            }
            scores.sort((o1, o2) -> (int) (o2 - o1));
            double median =
                    (scores.size() % 2 == 1)
                            ? scores.get(scores.size() / 2)
                            : (scores.get(scores.size() / 2) + scores.get(scores.size() / 2 - 1)) / 2;
            medians.put(i, median);
        }
        return medians;
    }

    @Override
    public Double getMedianPointsPerWeek(int matchupPeriod) {
        List<Double> scores = new ArrayList<>(teams.size());
        for (Team team : teams.values()) {
            scores.add(team.getPointsFor(matchupPeriod));
        }
        scores.sort((o1, o2) -> (int) (o2 - o1));
        return (scores.size() % 2 == 1)
                ? scores.get(scores.size() / 2)
                : (scores.get(scores.size() / 2) + scores.get(scores.size() / 2 - 1)) / 2;
    }

    @Override
    public double getPowerRankingScore(int teamId) {
        Team team = teams.get(teamId);
        double pointsVsMedian = 0;
        for (int i = 1; i < currentMatchupPeriod; i++) {
            pointsVsMedian += team.getPointsAgainst(i) - getMedianPointsPerWeek(i);
        }
        double curScore = team.getPointsFor()
                + (team.getPointsFor() * team.getWinPercentage())
                + team.getPointsFor()
                + pointsVsMedian;
        curScore /= currentMatchupPeriod;
        for (Player p : team.getPlayers()) {
            curScore += (p.getStatsMap().get("Last 30_" + getYear()) != null) ? p.getStatsMap().get("Last 30_" + getYear()).getAvg().get("FPTS") : 0;
        }
        return curScore;
    }

    @Override
    public List<Team> getPowerRankings() {
        List<Team> teams = new ArrayList<>(getTeams());
        teams.sort((o1, o2) -> (int) (o2.getPowerRankingScore() - o1.getPowerRankingScore()));
        return teams;
    }

    public Map<String, Map<Integer, ProTeamGame>> getProTeamMatchups() {
        return proTeamMatchups;
    }

    public void setProTeamMatchups(Map<String, Map<Integer, ProTeamGame>> proTeamMatchups) {
        this.proTeamMatchups = proTeamMatchups;
    }

    @Override
    public List<Map<String, List<String>>> getScheduleComparison() {
        List<Map<String, List<String>>> scheduleComparison = new ArrayList<>();
        for (Team compareTeam : getTeams()) {
            Map<String, List<String>> teamMap = new HashMap<>();
            teamMap.put("teamName", new ArrayList<>());
            teamMap.get("teamName").add(compareTeam.getName());
            teamMap.put("records", new ArrayList<>());
            for (Team scheduleTeam : getTeams()) {
                int[] record = compareSchedules(compareTeam.getTeamId(), scheduleTeam.getTeamId());
                String recordString = record[0] + "-" + record[1] + "-" + record[2];
                teamMap.get("records").add(recordString);
            }
            scheduleComparison.add(teamMap);
        }
        return scheduleComparison;
    }

    @Override
    public List<Map<String, List<String>>> getWeeklyComparison() {
        List<Map<String, List<String>>> weeklyComparison = new ArrayList<>();
        for (Team team : getTeams()) {
            Map<String, List<String>> teamMap = new HashMap<>();
            teamMap.put("teamName", new ArrayList<>());
            teamMap.get("teamName").add(team.getName());
            teamMap.put("records", new ArrayList<>());
            int wins = 0;
            int losses = 0;
            int ties = 0;
            for (int i = 1; i < getCurrentMatchupPeriod(); i++) {
                int[] record = weeklyRecord(i, team.getTeamId());
                String recordString = record[0] + "-" + record[1] + "-" + record[2];
                teamMap.get("records").add(recordString);
                wins += record[0];
                losses += record[1];
                ties += record[2];
            }
            teamMap.get("records").add(wins + "-" + losses + "-" + ties);
            weeklyComparison.add(teamMap);
        }
        return weeklyComparison;
    }

    @Override
    public List<JSONObject> getProjectedScores(String timePeriod, int matchupPeriod, boolean assessInjuries) {
        switch (timePeriod) {
            case "Last_7_2022" -> timePeriod = "Last 7_2022";
            case "Last_15_2022" -> timePeriod = "Last 15_2022";
            case "Last_30_2022" -> timePeriod = "Last 30_2022";
            case "Last_7_2023" -> timePeriod = "Last 7_2023";
            case "Last_15_2023" -> timePeriod = "Last 15_2023";
            case "Last_30_2023" -> timePeriod = "Last 30_2023";
        }

        List<JSONObject> projectedScores = new ArrayList<>();
        for (Team team : getTeams()) {
            projectedScores.add(getTeamProjectedScore(team, timePeriod, matchupPeriod, assessInjuries));
        }
        projectedScores.sort(Comparator.comparingDouble(o -> -Double.parseDouble(String.valueOf(o.get("points")))));
        return projectedScores;
    }

    @Override
    public List<JSONObject> getProTeamGames(int matchupPeriod) {
        List<JSONObject> list = new ArrayList<>();
        for (Map.Entry<String, Map<Integer, ProTeamGame>> entry : proTeamMatchups.entrySet()) {
            JSONObject jsonGames = new JSONObject();
            String teamName = entry.getKey();
            if (teamName.equals("FA")) continue;

            boolean[] games = new boolean[7];
            int start = (matchupPeriod - 1) * 7 + 1, end = matchupPeriod * 7;
            int count = 0;
            for (int i = start; i <= end; i++) {
                if (entry.getValue().get(i) != null) {
                    games[i - start] = true;
                    count++;
                }
            }


            jsonGames.put("teamName", teamName);
            jsonGames.put("games", games);
            jsonGames.put("count", count);
            list.add(jsonGames);
        }
        list.sort(Comparator.comparingInt(o -> -Integer.parseInt(String.valueOf(o.get("count")))));
        return list;
    }

    @Override
    public Double getWinPercentage(int homeTeamId, int awayTeamId, int numGames, int matchupPeriod, boolean assessInjuries, boolean reset) {
        Matchup matchup = getTeam(homeTeamId).getMatchups().get(matchupPeriod);
        if (matchup.getHomeTeamDailyLineups() == null || matchup.getAssessInjuries() != assessInjuries || matchup.getNumGames() != numGames || reset) {
            Map<Player, Map<DayOfWeek, Boolean>> homeTeamDailyLineups = getTeam(homeTeamId).getDailyLineups(matchupPeriod, assessInjuries, numGames, currentMatchupPeriod, currentScoringPeriod);
            NormalDistribution homeTeamNormal = getMatchupNormalDistribution(homeTeamDailyLineups, numGames, 0, 0);
            matchup.setHomeTeamNormal(homeTeamNormal);
            matchup.setHomeTeamDailyLineups(homeTeamDailyLineups);
        }

        if (matchup.getAwayTeamDailyLineups() == null || matchup.getAssessInjuries() != assessInjuries || matchup.getNumGames() != numGames || reset) {
            Map<Player, Map<DayOfWeek, Boolean>> awayTeamDailyLineups = getTeam(awayTeamId).getDailyLineups(matchupPeriod, assessInjuries, numGames, currentMatchupPeriod, currentScoringPeriod);
            NormalDistribution awayTeamNormal = getMatchupNormalDistribution(awayTeamDailyLineups, numGames, 0, 0);
            matchup.setAwayTeamNormal(awayTeamNormal);
            matchup.setAwayTeamDailyLineups(awayTeamDailyLineups);
        }

        matchup.setNumGames(numGames);
        matchup.setAssessInjuries(assessInjuries);

        if (matchup.getHomeTeamNormal() == null || matchup.getAwayTeamNormal() == null)
            return (matchup.getHomePoints() > matchup.getAwayPoints()) ? 1.0 : 0.0;
        return matchup.getHomeTeamWinPercentage(matchup.getAwayPoints() - matchup.getHomePoints());
    }

    @Override
    public Double getWinPercentage(Matchup matchup, int numGames, boolean assessInjuries, boolean reset) {
        return getWinPercentage(matchup.getHomeTeamId(), matchup.getAwayTeamId(), numGames, matchup.getMatchupPeriod(), assessInjuries, reset);
    }


    @Override
    public void setDailyLineUps(int teamId, int numGames, int matchupPeriod, Map<Player, Map<DayOfWeek, Boolean>> dailyLineUps, double meanAdj, double varAdj) {
        Matchup matchup = getTeam(teamId).getMatchups().get(matchupPeriod);
        NormalDistribution normal = getMatchupNormalDistribution(dailyLineUps, numGames, meanAdj, varAdj);
        if (teamId == matchup.getHomeTeamId()) {
            matchup.setHomeTeamNormal(normal);
            matchup.setHomeTeamDailyLineups(dailyLineUps);
        } else if (teamId == matchup.getAwayTeamId()) {
            matchup.setAwayTeamNormal(normal);
            matchup.setAwayTeamDailyLineups(dailyLineUps);
        }

    }


    //    @Override
    @Deprecated
    public Double getWinPercentage1(int homeTeamId, int awayTeamId, int numGames, int matchupPeriod, boolean assessInjuries) {
        TreeSet<Pair<Pair<Double, Double>, Player>> hpq = new TreeSet<>(Collections.reverseOrder(Comparator.comparingDouble(pairPlayerPair -> pairPlayerPair.getKey().getValue())));
        TreeSet<Pair<Pair<Double, Double>, Player>> apq = new TreeSet<>(Collections.reverseOrder(Comparator.comparingDouble(pairPlayerPair -> pairPlayerPair.getKey().getValue())));

        for (Player p : getTeam(homeTeamId).getPlayers()) {
            Pair<Double, Double> varianceAndMean = p.calculateVarianceAndMean(numGames);
            hpq.add(new Pair<>(varianceAndMean, p));
        }

        for (Player p : getTeam(awayTeamId).getPlayers()) {
            Pair<Double, Double> varianceAndMean = p.calculateVarianceAndMean(numGames);
            apq.add(new Pair<>(varianceAndMean, p));
        }

        Pair<Double, Double> htvm = totalVarianceAndMean(getTeam(homeTeamId), matchupPeriod, hpq, assessInjuries);
        Pair<Double, Double> atvm = totalVarianceAndMean(getTeam(awayTeamId), matchupPeriod, apq, assessInjuries);
        double mean = htvm.getValue() - atvm.getValue();
        double var = htvm.getKey() + atvm.getKey();
        System.out.println(htvm.getValue() + " vs " + atvm.getValue() + " ----- " + Math.sqrt(htvm.getKey()) + " vs " + Math.sqrt(atvm.getKey()));
        NormalDistribution distribution = new NormalDistribution(mean, Math.sqrt(var));
        return 1 - distribution.cumulativeProbability(getTeam(awayTeamId).getPointsFor(matchupPeriod) - getTeam(homeTeamId).getPointsFor(matchupPeriod));
    }

    @Override
    public List<String> getRosteredPlayerIds() {
        List<String> rosteredPlayerIds = new ArrayList<>();
        for (Team team : teams.values())
            for (Player player : team.getPlayers()) rosteredPlayerIds.add(player.getPlayerId());
        return rosteredPlayerIds;
    }

    private JSONObject getTeamProjectedScore(Team team, String timePeriod, int matchupPeriod, boolean assessInjuries) {
        double points = team.getPointsFor(matchupPeriod);
        int totalGames = 0;

        int start;
        int end = matchupPeriod * 7;
        if (matchupPeriod == currentMatchupPeriod) start = currentScoringPeriod;
        else if (matchupPeriod > currentMatchupPeriod) start = (matchupPeriod - 1) * 7 + 1;
        else start = end;

        for (int i = start; i <= end; i++) {
            PriorityQueue<Double> pq = new PriorityQueue<>();
            for (Player player : team.getPlayers()) {
                if (proTeamMatchups.get(player.getProTeam()).get(i) != null && !proTeamMatchups.get(player.getProTeam()).get(i).hasHappened()) {
                    double playerPoints = (player.getStatsMap().get(timePeriod) == null) ? 0 : player.getStatsMap().get(timePeriod).getAvg().get("FPTS");
                    if (!assessInjuries || (player.getInjuryStatus().equals("ACTIVE") || player.getInjuryStatus().equals("DAY_TO_DAY")))
                        pq.add(playerPoints);
                }
            }
            while (pq.size() > 10) {
                pq.poll();
            }
            totalGames += pq.size();

            while (!pq.isEmpty()) points += pq.poll();

        }

        JSONObject jsonProjected = new JSONObject();
        jsonProjected.put("teamName", team.getName());
        jsonProjected.put("totalGames", totalGames);
        jsonProjected.put("points", ((int) (points * 100)) / 100.00);
        return jsonProjected;
    }

    private Pair<Double, Double> totalVarianceAndMean(Team team, int matchupPeriod, TreeSet<Pair<Pair<Double, Double>, Player>> pq, boolean assessInjuries) {
        double mean = 0, variance = 0;

        int start;
        int end = matchupPeriod * 7;
        if (matchupPeriod == currentMatchupPeriod) start = currentScoringPeriod;
        else if (matchupPeriod > currentMatchupPeriod) start = (matchupPeriod - 1) * 7 + 1;
        else start = end;

        for (int i = start; i <= end; i++) {
            int count = 0;
            for (Pair<Pair<Double, Double>, Player> p : pq) {
                if (count >= 10) break;
                if (proTeamMatchups.get(p.getValue().getProTeam()).get(i) != null && !proTeamMatchups.get(p.getValue().getProTeam()).get(i).hasHappened() && (!assessInjuries || (p.getValue().getInjuryStatus().equals("ACTIVE") || p.getValue().getInjuryStatus().equals("DAY_TO_DAY")))) {
                    mean += p.getKey().getValue();
                    variance += p.getKey().getKey();
                    count++;
                }
            }
        }
        return new Pair<>(variance, mean);
    }

    private NormalDistribution getMatchupNormalDistribution(Map<Player, Map<DayOfWeek, Boolean>> gamesPerPlayer, int numRecentGames, double meanAdj, double varAdj) {
        double mean = meanAdj;
        double var = varAdj;
        for (Map.Entry<Player, Map<DayOfWeek, Boolean>> e : gamesPerPlayer.entrySet()) {
            int games = Collections.frequency(e.getValue().values(), true);
            Pair<Double, Double> varianceAndMean = e.getKey().calculateVarianceAndMean(numRecentGames);
            mean += varianceAndMean.getValue() * games;
            var += varianceAndMean.getKey() * games;
        }
        if (var == 0) return null;
        return new NormalDistribution(mean, Math.sqrt(var));
    }
}
