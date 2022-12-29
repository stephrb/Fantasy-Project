// import axios from "axios";
import AxiosInstance from "./AxiosInstance";

const MODEL_API_BASE_URL =
  "https://fba-backend-production.up.railway.app/api/v1/";
// const MODEL_API_BASE_URL = "http://localhost:8080/api/v1/"

class ModelService {
  getPowerRankings(signal) {
    return AxiosInstance.get(MODEL_API_BASE_URL + "rankings", signal);
  }

  createModel(leagueId) {
    localStorage.setItem("leagueId", leagueId["leagueId"]);
    localStorage.setItem(
      "userId",
      Math.floor(100000 + Math.random() * 900000).toString()
    );
    return AxiosInstance.post(MODEL_API_BASE_URL + "create", leagueId);
  }

  createDemo() {
    localStorage.setItem("leagueId", "DEMO###1117484973");
    localStorage.setItem(
      "userId",
      Math.floor(100000 + Math.random() * 900000).toString()
    );
    return AxiosInstance.post(MODEL_API_BASE_URL + "demo");
  }

  modelGenerated() {
    return AxiosInstance.post(MODEL_API_BASE_URL + "request");
  }

  getScheduleComparison(signal) {
    console.log(localStorage.getItem("leagueId"));
    return AxiosInstance.get(MODEL_API_BASE_URL + "compareSchedules", signal);
  }

  getWeeklyComparison(signal) {
    return AxiosInstance.get(MODEL_API_BASE_URL + "compareWeekly", signal);
  }

  getPlayoffRankings(signal) {
    return AxiosInstance.get(MODEL_API_BASE_URL + "playoffRankings", signal);
  }

  getRemainingMatchupPeriods(signal) {
    return AxiosInstance.get(MODEL_API_BASE_URL + "remainingMatchupPeriods", signal);
  }

  getAllMatchups(signal) {
    return AxiosInstance.get(MODEL_API_BASE_URL + "allMatchups", signal);
  }

  getPlayoffMachineMatchups(signal) {
    return AxiosInstance.get(MODEL_API_BASE_URL + "playoffMachineMatchups", signal);
  }

  setWinnerHome(matchup) {
    return AxiosInstance.post(MODEL_API_BASE_URL + "setWinnerHome", matchup);
  }

  setWinnerAway(matchup) {
    return AxiosInstance.post(MODEL_API_BASE_URL + "setWinnerAway", matchup);
  }

  setWinnerTie(matchup) {
    return AxiosInstance.post(MODEL_API_BASE_URL + "setWinnerTie", matchup);
  }
  getIsSorted(signal) {
    return AxiosInstance.get(MODEL_API_BASE_URL + "isSorted", signal);
  }

  resetPlayoffMachine() {
    return AxiosInstance.post(MODEL_API_BASE_URL + "resetPlayoffMachine");
  }

  getProjectedScores(timePeriod, matchupPeriod, assessInjuries, signal) {
    return AxiosInstance.get(
      MODEL_API_BASE_URL +
        "scoreProjections?timePeriod=" +
        timePeriod +
        "&matchupPeriod=" +
        matchupPeriod +
        "&assessInjuries=" +
        assessInjuries, signal
    );
  }

  getCurrentMatchupPeriod(signal) {
    return AxiosInstance.get(MODEL_API_BASE_URL + "currentMatchupPeriod", signal);
  }

  getProTeamGames(matchupPeriod, signal) {
    return AxiosInstance.get(
      MODEL_API_BASE_URL + "proTeamGames?matchupPeriod=" + matchupPeriod, signal
    );
  }
}

export default new ModelService();
