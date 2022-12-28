package fba.utils;

import fba.model.*;
import fba.model.player.Player;
import fba.model.player.PlayerImpl;
import fba.model.player.PlayerStats;
import fba.model.player.PlayerStatsImpl;
import fba.model.proteams.ProTeamSchedules;
import fba.model.team.*;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public final class Factory {
  /**
   * @param jsonLeague the json object that contains ESPN league information (endpoint is "")
   * @return a Model.Model.League object that contains the information in the json object
   */
  public static League createLeague(JSONObject jsonLeague) {
    int year = Integer.parseInt(String.valueOf(jsonLeague.get("seasonId")));
    String leagueId = String.valueOf(jsonLeague.get("id"));
    int currentScoringPeriod = Integer.parseInt(String.valueOf(jsonLeague.get("scoringPeriodId")));
    int currentMatchupPeriod =
        Integer.parseInt(
            String.valueOf(((JSONObject) jsonLeague.get("status")).get("currentMatchupPeriod")));
    String name = String.valueOf(((JSONObject) jsonLeague.get("settings")).get("name"));

    League league = new LeagueImpl(leagueId, name);
    league.setProTeamMatchups(ProTeamSchedules.getProTeamMatchups());
    league.setYear(year);
    league.setCurrentMatchupPeriod(currentMatchupPeriod);
    league.setCurrentScoringPeriod(currentScoringPeriod);
    return league;
  }

  /**
   * @param league the Model.Model.League that the teams should be added to
   * @param jsonLeague the JSON object that holds the information about each team (endpoint is
   *     mTeam)
   */
  public static void setTeams(League league, JSONObject jsonLeague) {
    JSONArray jsonTeams = (JSONArray) jsonLeague.get("teams");

    for (Object team : jsonTeams) {
      JSONObject jsonTeam = (JSONObject) team;
      String abbrev = String.valueOf(jsonTeam.get("abbrev"));
      int teamId = Integer.parseInt(String.valueOf(jsonTeam.get("id")));
      int divisionId = Integer.parseInt(String.valueOf(jsonTeam.get("divisionId")));
      String location = String.valueOf(jsonTeam.get("location"));
      String nickname = String.valueOf(jsonTeam.get("nickname"));
      int playoffSeed = Integer.parseInt(String.valueOf(jsonTeam.get("playoffSeed")));

      JSONObject jsonRecord = ((JSONObject) ((JSONObject) jsonTeam.get("record")).get("overall"));
      int wins = Integer.parseInt(String.valueOf(jsonRecord.get("wins")));
      int losses = Integer.parseInt(String.valueOf(jsonRecord.get("losses")));
      int ties = Integer.parseInt(String.valueOf(jsonRecord.get("ties")));
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
      for (Object key : jsonMatchupAcquisitions.keySet()) {
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
              ties,
              pointsFor,
              pointsAgainst,
              gamesBack,
              matchUpAcquisitionsPerWeek,
              moveToActive,
              drops,
              playoffSeed,
              totalAcquisitions,
              divisionId));
    }
  }

  /**
   * @param league the Model.Model.League object where the rosters should be set
   * @param jsonRosters the JSON object that holds information on the rosters (endpoint is mRoster)
   */
  public static void setRosters(League league, JSONObject jsonRosters) {
    JSONArray jsonRostersArr = (JSONArray) jsonRosters.get("teams");

    for (Object o : jsonRostersArr) {
      JSONObject jsonTeam = (JSONObject) o;
      int teamId = Integer.parseInt(String.valueOf(jsonTeam.get("id")));
      JSONArray jsonPlayersArr = ((JSONArray) ((JSONObject) jsonTeam.get("roster")).get("entries"));

      for (Object value : jsonPlayersArr) {
        JSONObject jsonEntry = (JSONObject) value;
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

  /**
   * @param jsonStats the JSON object that contains ESPN formatted stats information for a pro
   *     player
   * @return the playerStats object that contains the same information as the JSON object
   */
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
          Request.parseStats((JSONObject) jsonStats.get("averageStats")),
          Request.parseStats((JSONObject) jsonStats.get("stats")),
          fPtsAvg,
          fPtsTot);
    } else {

      return new PlayerStatsImpl(new HashMap<>(), new HashMap<>(), 0, 0);
    }
  }

  /**
   * @param jsonPlayer the JSON object that contains player information
   * @return the Utils.Model.Model.Player.Player object that holds the same information as the JSON
   *     object
   */
  public static Player createPlayer(JSONObject jsonPlayer) {

    int playerId = Integer.parseInt(String.valueOf(jsonPlayer.get("id")));
    String firstName = String.valueOf(jsonPlayer.get("firstName"));
    String lastName = String.valueOf(jsonPlayer.get("lastName"));
    String fullName = String.valueOf(jsonPlayer.get("fullName"));
    String injuryStatus = String.valueOf(jsonPlayer.get("injuryStatus"));
    int proTeamId = Integer.parseInt(String.valueOf(jsonPlayer.get("proTeamId")));
    int position = Integer.parseInt(String.valueOf(jsonPlayer.get("defaultPositionId")));
    JSONObject jsonOwnership = ((JSONObject) jsonPlayer.get("ownership"));
    double percentOwned =
        (jsonOwnership == null)
            ? 0
            : Double.parseDouble(String.valueOf(jsonOwnership.get("percentOwned")));
    double percentChange =
        (jsonOwnership == null)
            ? 0
            : Double.parseDouble(String.valueOf(jsonOwnership.get("percentChange")));
    double percentStarted =
        (jsonOwnership == null)
            ? 0
            : Double.parseDouble(String.valueOf(jsonOwnership.get("percentStarted")));
    double averageDraftPosition =
        (jsonOwnership == null)
            ? 0
            : Double.parseDouble(String.valueOf(jsonOwnership.get("averageDraftPosition")));
    JSONArray jsonEligibleSlotsArr = ((JSONArray) jsonPlayer.get("eligibleSlots"));
    List<Integer> eligibleSlots = new ArrayList<>();
    for (Object o : jsonEligibleSlotsArr) {
      int slot = Integer.parseInt(String.valueOf(o));
      eligibleSlots.add(slot);
    }

    Map<String, PlayerStats> statsMap = new HashMap<>();
    JSONArray jsonStatsArr = ((JSONArray) jsonPlayer.get("stats"));
    if (jsonStatsArr != null) {
      for (Object o : jsonStatsArr) {
        JSONObject jsonStat = ((JSONObject) o);
        String statId =
            MapConstants.statIdMap.get(
                    String.valueOf(jsonStat.get("statSourceId")) + jsonStat.get("statSplitTypeId"))
                + "_"
                + jsonStat.get("seasonId");
        statsMap.put(statId, createPlayerStat(jsonStat));
      }
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

  /**
   * @param jsonPlayers the JSON array that holds all the players (endpoint is kona_player_info)
   * @return the list of free agents that corresponds to the input JSON array
   */
  public static Pair<List<Player>, Map<String, Player>> createPlayers(League league, JSONArray jsonPlayers) {
    List<Player> freeAgents = new ArrayList<>();
    Map<String, Player> allPlayers = new HashMap<>();
    for (Object player : jsonPlayers) {
      JSONObject jsonPlayerEntry = ((JSONObject) player);
      String status = String.valueOf(jsonPlayerEntry.get("status"));
      JSONObject jsonPlayer = ((JSONObject) jsonPlayerEntry.get("player"));
      Player p = createPlayer(jsonPlayer);
      if (status.equals("ONTEAM")) {
          league.addPlayer(p, Integer.parseInt(String.valueOf(jsonPlayerEntry.get("onTeamId"))));
      }

      allPlayers.put(p.getPlayerId(), p);
      if (status.equals("FREEAGENT") || status.equals("WAIVERS")) {
        freeAgents.add(p);
      }
    }

    return new Pair<>(freeAgents, allPlayers);
  }

  /**
   * @param league the league where the matchups will be set
   * @param jsonMatchups the JSON object that contains all the data (endpoint is mBoxscore)
   */
  public static void setMatchups(League league, JSONObject jsonMatchups) {
    league.setFinalScoringPeriod(
        Integer.parseInt(
            String.valueOf(((JSONObject) jsonMatchups.get("status")).get("finalScoringPeriod"))));

    JSONArray jsonSchedule = (JSONArray) jsonMatchups.get("schedule");
    int matchupId = 0;
    for (Object json : jsonSchedule) {
      JSONObject jsonMatchup = (JSONObject) json;
      int matchupPeriod = Integer.parseInt(String.valueOf(jsonMatchup.get("matchupPeriodId")));
      int homeTeamId =
          Integer.parseInt(String.valueOf(((JSONObject) jsonMatchup.get("home")).get("teamId")));
      double homePoints =
          Double.parseDouble(
              String.valueOf(((JSONObject) jsonMatchup.get("home")).get("totalPoints")));
      int awayTeamId = -1;
      double awayPoints = -1;
      if (jsonMatchup.get("away") != null) {
        awayTeamId =
            Integer.parseInt(String.valueOf(((JSONObject) jsonMatchup.get("away")).get("teamId")));
        awayPoints =
            Double.parseDouble(
                String.valueOf(((JSONObject) jsonMatchup.get("away")).get("totalPoints")));
      }
      boolean isPlayed = matchupPeriod < league.getCurrentMatchupPeriod();
      String homeTeamName = league.getTeam(homeTeamId).getName();
      String awayTeamName = (awayTeamId == -1) ? "Bye Week" : league.getTeam(awayTeamId).getName();
      Matchup matchup =
          new MatchupImpl(
              homeTeamId,
              homePoints,
              awayTeamId,
              awayPoints,
              isPlayed,
              homeTeamName,
              awayTeamName,
              matchupId++,
              matchupPeriod);
      league.addMatchup(matchupPeriod, matchup);
    }
  }

  public static Model createModel(String leagueId) {
    String year = "2023";
    String leagueInfo = Request.get(leagueId, year, "", "");
    JSONObject jsonLeague = Request.parseString(leagueInfo);
    League league = Factory.createLeague(jsonLeague);

    String teamInfo = Request.get(leagueId, year, "?view=mTeam", "");
    JSONObject jsonTeam = Request.parseString(teamInfo);
    Factory.setTeams(league, jsonTeam);

    String matchupInfo = Request.get(leagueId, year, "?view=mBoxscore", "");
    JSONObject jsonMatchups = Request.parseString(matchupInfo);
    Factory.setMatchups(league, jsonMatchups);

    for (Team team : league.getTeams())
      team.setPowerRankingScore(league.getPowerRankingScore(team.getTeamId()));

    String header =
        "{\"players\":{\"limit\":1500,\"sortDraftRanks\":{\"sortPriority\":100,\"sortAsc\":true,\"value\":\"STANDARD\"}}}";
    String playersInfo =
        Request.get(leagueId, year, "?view=kona_player_info", header);
    JSONObject jsonPlayers = Request.parseString(playersInfo);
    JSONArray jsonPlayersArr = (JSONArray) jsonPlayers.get("players");

    Pair<List<Player>, Map<String, Player>> players = createPlayers(league, jsonPlayersArr);
    league.setFreeAgents(players.getKey());
    league.setAllPlayers(players.getValue());

    setPreviousPlayerScores(league);

    String draftInfo = Request.get(leagueId, year, "?view=mDraftDetail", "");
    JSONObject jsonDraft = Request.parseString(draftInfo);
    JSONObject jsonDraftDetail= (JSONObject) jsonDraft.get("draftDetail");
    league.setDraftPicks(Factory.createDraftPicks(jsonDraftDetail));

    return new ModelImpl(league, new PlayoffMachineImpl(league));
  }

  public static Model createDemo() {
    Model model = createModel("1117484973");
    String[] teamNames =
        new String[] {
          "Jokic's Jokers",
          "Los Angeles Retirement Home",
          "LeMickey Jamison",
          "Russ's Brick House",
          "Splash Bros",
          "Anthony Day-to-Davis",
          "The Greek Freaks",
          "The Dawgs"
        };
    int i = 0;
    for (Team team : model.getRankings()) {
      model.getTeam(team.getTeamId()).setName(teamNames[i]);
      team.setName(teamNames[i++]);
    }
    model.updateMatchupNames();
    return model;
  }

  public static void setPreviousPlayerScores(League league) {
    String playerIds = String.join(", ", league.getRosteredPlayerIds());
    if (playerIds.isEmpty()) return;
    String header =
        "{\"players\":{\"filterIds\":{\"value\":["
            + playerIds
            + "]},\"filterStatsForTopScoringPeriodIds\":{\"value\":" + league.getCurrentScoringPeriod() + "}}}";
    String recentGames =
        Request.get("1117484973", "2023", "?view=kona_playercard", header);
    JSONObject jsonRecentGames = Request.parseString(recentGames);

    JSONArray jsonPlayers = (JSONArray) jsonRecentGames.get("players");

    for (Object _player : jsonPlayers) {
      JSONObject jsonPlayer = (JSONObject) _player;
      List<Double> previousGameScores = getPreviousPlayerScores(jsonPlayer, league.getCurrentScoringPeriod());
      String playerId = String.valueOf(jsonPlayer.get("id"));
      int teamId = Integer.parseInt(String.valueOf(jsonPlayer.get("onTeamId")));
      if (teamId == 0)
        continue;

      league.getAllPlayers().get(playerId).setPreviousGameScores(previousGameScores);
    }
  }

  private static List<Double> getPreviousPlayerScores(JSONObject jsonPlayer, int currentScoringPeriod) {
    List<Double> previousPlayerScores = new ArrayList<>();
    JSONArray jsonStats = (JSONArray) ((JSONObject) jsonPlayer.get("player")).get("stats");

    for (Object stat : jsonStats) {
      JSONObject jsonStat = (JSONObject) stat;
      if (Integer.parseInt(String.valueOf(jsonStat.get("scoringPeriodId"))) > currentScoringPeriod) break;
      if (((JSONObject) jsonStat.get("stats")).isEmpty()) continue;
      previousPlayerScores.add((Double) jsonStat.get("appliedTotal"));
    }
    return previousPlayerScores;
  }

  private static Map<Integer, DraftPick> createDraftPicks(JSONObject jsonDraftDetail) {
    Map<Integer, DraftPick> draftPicks = new HashMap<>();
    if (jsonDraftDetail.get("drafted").toString().equals("false")|| jsonDraftDetail.get("inProgress").toString().equals("true")) return draftPicks;
    JSONArray jsonPicks = (JSONArray) jsonDraftDetail.get("picks");

    for (Object objPick : jsonPicks) {
      JSONObject jsonPick = (JSONObject) objPick;
      String playerId = jsonPick.get("playerId").toString();
      int pickNumber = Integer.parseInt(jsonPick.get("id").toString()), roundId = Integer.parseInt(jsonPick.get("roundId").toString()), teamId = Integer.parseInt(jsonPick.get("teamId").toString()), roundPickNumber = Integer.parseInt(jsonPick.get("roundPickNumber").toString());
      draftPicks.put(pickNumber, new DraftPickImpl(playerId, roundId, teamId, roundPickNumber, pickNumber));
    }
    return draftPicks;
  }

}
