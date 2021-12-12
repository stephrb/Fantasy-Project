import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class Factory {
  public static League createLeague(JSONObject jsonLeague) {
    int year = Integer.parseInt(String.valueOf(jsonLeague.get("seasonId")));
    String leagueId = String.valueOf(jsonLeague.get("id"));
    int currentScoringPeriod = Integer.parseInt(String.valueOf(jsonLeague.get("scoringPeriodId")));
    int currentMatchupPeriod =
        Integer.parseInt(
            String.valueOf(((JSONObject) jsonLeague.get("status")).get("currentMatchupPeriod")));
    String name = String.valueOf(((JSONObject) jsonLeague.get("settings")).get("name"));

    League league = new LeagueImpl(leagueId, name);
    league.setYear(year);
    league.setCurrentMatchupPeriod(currentMatchupPeriod);
    league.setCurrentScoringPeriod(currentScoringPeriod);
    return league;
  }

  public static void createTeams(League league, JSONObject jsonLeague) {
    JSONArray jsonTeams = (JSONArray) jsonLeague.get("teams");

    for (int i = 0; i < jsonTeams.size(); i++) {
      JSONObject jsonTeam = (JSONObject) jsonTeams.get(i);
      String abbrev = String.valueOf(jsonTeam.get("abbrev"));
      int teamId = Integer.parseInt(String.valueOf(jsonTeam.get("id")));
      String location = String.valueOf(jsonTeam.get("location"));
      String nickname = String.valueOf(jsonTeam.get("nickname"));
      int playoffSeed = Integer.parseInt(String.valueOf(jsonTeam.get("playoffSeed")));

      JSONObject jsonRecord = ((JSONObject) ((JSONObject) jsonTeam.get("record")).get("overall"));
      int wins = Integer.parseInt(String.valueOf(jsonRecord.get("wins")));
      int losses = Integer.parseInt(String.valueOf(jsonRecord.get("losses")));
      double pointsFor = Double.parseDouble(String.valueOf(jsonRecord.get("pointsFor")));
      double pointsAgainst = Double.parseDouble(String.valueOf(jsonRecord.get("pointsAgainst")));
      double gamesBack = Double.parseDouble(String.valueOf(jsonRecord.get("gamesBack")));

      JSONObject jsonTransactions = ((JSONObject) jsonTeam.get("transactionCounter"));
      int moveToActive = Integer.parseInt(String.valueOf(jsonTransactions.get("moveToActive")));
      int drops = Integer.parseInt(String.valueOf(jsonTransactions.get("drops")));
      int totalAcquisitions =
          Integer.parseInt(String.valueOf(jsonTransactions.get("acquisitions")));

      JSONObject jsonMatchupAcquisitions =
          ((JSONObject) jsonTransactions.get("matchupAcquisitionTotals"));
      Map<Integer, Integer> matchUpAcquisitionsPerWeek = new HashMap<>();
      Iterator iterator = jsonMatchupAcquisitions.keySet().iterator();
      while (iterator.hasNext()) {
        Object key = iterator.next();

        int val = Integer.parseInt(String.valueOf(jsonMatchupAcquisitions.get(key)));
        matchUpAcquisitionsPerWeek.put(Integer.parseInt(String.valueOf(key)), val);
      }

      league.addTeam(
          teamId,
          new TeamImpl(
              nickname,
              location,
              abbrev,
              teamId,
              wins,
              losses,
              pointsFor,
              pointsAgainst,
              gamesBack,
              matchUpAcquisitionsPerWeek,
              moveToActive,
              drops,
              playoffSeed,
              totalAcquisitions));
    }
  }

  public static void setRosters(League league, JSONObject jsonRosters) {
    JSONArray jsonRostersArr = (JSONArray) jsonRosters.get("teams");

    for (int i = 0; i < jsonRostersArr.size(); i++) {
      JSONObject jsonTeam = (JSONObject) jsonRostersArr.get(i);
      int teamId = Integer.parseInt(String.valueOf(jsonTeam.get("id")));
      JSONArray jsonPlayersArr = ((JSONArray) ((JSONObject) jsonTeam.get("roster")).get("entries"));

      for (int j = 0; j < jsonPlayersArr.size(); j++) {
        JSONObject jsonEntry = (JSONObject) jsonPlayersArr.get(j);
        int lineupSlotId = Integer.parseInt(String.valueOf(jsonEntry.get("lineupSlotId")));
        String acquisitionDate = (String.valueOf(jsonEntry.get("acquisitionDate")));
        JSONObject jsonPlayer =
            ((JSONObject) ((JSONObject) jsonEntry.get("playerPoolEntry")).get("player"));
        Player player = createPlayer(jsonPlayer);
        player.setAcquisitionDate(acquisitionDate);
        player.setLineUpSlotId(lineupSlotId);
        league.addPlayer(player, teamId);
      }
    }
  }

  public static PlayerStats createPlayerStat(JSONObject jsonStats) {

    if (jsonStats != null) {
      double fPtsAvg =
          (jsonStats.get("appliedAverage") != null)
              ? Double.parseDouble(String.valueOf(jsonStats.get("appliedAverage")))
              : 0;
      double fPtsTot =
          (jsonStats.get("appliedTotal") != null)
              ? Double.parseDouble(String.valueOf(jsonStats.get("appliedTotal")))
              : 0;

      return new PlayerStatsImpl(
          Utils.parseStats((JSONObject) jsonStats.get("averageStats")),
          Utils.parseStats((JSONObject) jsonStats.get("stats")),
          fPtsAvg,
          fPtsTot);
    } else {

      return new PlayerStatsImpl(new HashMap<>(), new HashMap<>(), 0, 0);
    }
  }

  public static Player createPlayer(JSONObject jsonPlayer) {

    int playerId = Integer.parseInt(String.valueOf(jsonPlayer.get("id")));
    String firstName = String.valueOf(jsonPlayer.get("firstName"));
    String lastName = String.valueOf(jsonPlayer.get("lastName"));
    String fullName = String.valueOf(jsonPlayer.get("fullName"));
    String injuryStatus = String.valueOf(jsonPlayer.get("injuryStatus"));
    int proTeamId = Integer.parseInt(String.valueOf(jsonPlayer.get("proTeamId")));
    int position = Integer.parseInt(String.valueOf(jsonPlayer.get("defaultPositionId")));
    JSONObject jsonOwnership = ((JSONObject) jsonPlayer.get("ownership"));
    double percentOwned = Double.parseDouble(String.valueOf(jsonOwnership.get("percentOwned")));
    double percentChange = Double.parseDouble(String.valueOf(jsonOwnership.get("percentChange")));
    double percentStarted = Double.parseDouble(String.valueOf(jsonOwnership.get("percentStarted")));
    double averageDraftPosition =
        Double.parseDouble(String.valueOf(jsonOwnership.get("averageDraftPosition")));
    JSONArray jsonEligibleSlotsArr = ((JSONArray) jsonPlayer.get("eligibleSlots"));
    List<Integer> eligibleSlots = new ArrayList<>();
    for (int k = 0; k < jsonEligibleSlotsArr.size(); k++) {
      int slot = Integer.parseInt(String.valueOf(jsonEligibleSlotsArr.get(k)));
      eligibleSlots.add(slot);
    }

    Map<String, PlayerStats> statsMap = new HashMap<>();
    JSONArray jsonStatsArr = ((JSONArray) jsonPlayer.get("stats"));
    for (int k = 0; k < jsonStatsArr.size(); k++) {
      JSONObject jsonStat = ((JSONObject) jsonStatsArr.get(k));
      String statId =
          MapConstants.statIdMap.get(
                  String.valueOf(jsonStat.get("statSourceId")) + jsonStat.get("statSplitTypeId"))
              + "  "
              + jsonStat.get("seasonId");
      statsMap.put(statId, createPlayerStat(jsonStat));
    }
    return new PlayerImpl(
        firstName,
        lastName,
        fullName,
        playerId,
        proTeamId,
        percentOwned,
        percentChange,
        percentStarted,
        averageDraftPosition,
        injuryStatus,
        position,
        statsMap,
        eligibleSlots);
  }

  public static List<Player> createFreeAgents(JSONArray jsonPlayers) {
    List<Player> freeAgents = new ArrayList<>();
    for (int i = 0; i < jsonPlayers.size(); i++) {
      JSONObject jsonPlayerEntry = ((JSONObject) jsonPlayers.get(i));
      String status = String.valueOf(jsonPlayerEntry.get("status"));
      if (status.equals("FREEAGENT") || status.equals("WAIVERS")) {
        JSONObject jsonPlayer = ((JSONObject) jsonPlayerEntry.get("player"));
        freeAgents.add(createPlayer(jsonPlayer));
      }
    }

    return freeAgents;
  }
}
