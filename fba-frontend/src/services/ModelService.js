
// import axios from "axios";
import AxiosInstance from "./AxiosInstance";

const MODEL_API_BASE_URL = "https://fba-backend-production.up.railway.app/api/v1/";
// const MODEL_API_BASE_URL = "http://localhost:8080/api/v1/"

class ModelService {

    getPowerRankings() {
        return AxiosInstance.get(MODEL_API_BASE_URL + "rankings");
    }

    createModel(leagueId) {
        localStorage.setItem('leagueId', leagueId['leagueId']);
        localStorage.setItem('userId', Math.floor(100000 + Math.random() * 900000).toString())
        return AxiosInstance.post(MODEL_API_BASE_URL + "create", leagueId);
    }

    createDemo() {
        localStorage.setItem('leagueId', "DEMO###1117484973");
        localStorage.setItem('userId', Math.floor(100000 + Math.random() * 900000).toString())
        return AxiosInstance.post(MODEL_API_BASE_URL + "demo");
    }

    modelGenerated() {
        return AxiosInstance.post(MODEL_API_BASE_URL + "request");
    }

    getScheduleComparison() {
        console.log(localStorage.getItem('leagueId'))
        return AxiosInstance.get(MODEL_API_BASE_URL + "compareSchedules");
    }

    getWeeklyComparison() {
        return AxiosInstance.get(MODEL_API_BASE_URL + "compareWeekly");
    }

    getPlayoffRankings() {
        return AxiosInstance.get(MODEL_API_BASE_URL + "playoffRankings");
    }

    getRemainingMatchupPeriods() {
        return AxiosInstance.get(MODEL_API_BASE_URL + "remainingMatchupPeriods");
    }

    getAllMatchups() {
        return AxiosInstance.get(MODEL_API_BASE_URL + "allMatchups");
    }

    getPlayoffMachineMatchups() {
        return AxiosInstance.get(MODEL_API_BASE_URL + "playoffMachineMatchups");
    }

    setWinnerHome(matchup) {
        return AxiosInstance.post(MODEL_API_BASE_URL + "setWinnerHome", matchup);
    }

    setWinnerAway(matchup) {
        return AxiosInstance.post(MODEL_API_BASE_URL + "setWinnerAway", matchup);
    }

    setWinnerTie(matchup) {
        return AxiosInstance.post(MODEL_API_BASE_URL + "setWinnerTie", matchup)
    }
    getIsSorted() {
        return AxiosInstance.get(MODEL_API_BASE_URL + "isSorted");
    }

    resetPlayoffMachine() {
        return AxiosInstance.post(MODEL_API_BASE_URL + "resetPlayoffMachine");
    }

    getProjectedScores(timePeriod, matchupPeriod, assessInjuries) {
        return AxiosInstance.get(MODEL_API_BASE_URL + "scoreProjections?timePeriod=" + timePeriod + "&matchupPeriod=" + matchupPeriod + "&assessInjuries=" + assessInjuries);
    }

    getCurrentMatchupPeriod() {
        return AxiosInstance.get(MODEL_API_BASE_URL + "currentMatchupPeriod");
    }

    getProTeamGames(matchupPeriod) {
        return AxiosInstance.get(MODEL_API_BASE_URL + "proTeamGames?matchupPeriod=" + matchupPeriod);
    }
}


export default new ModelService()