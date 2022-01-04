import axios from 'axios'

const MODEL_API_BASE_URL = "http://localhost:8080/api/v1/";

class ModelService {
    getPowerRankings() {
        return axios.get(MODEL_API_BASE_URL + "rankings");
    }

    createModel(leagueId) {
        return axios.post(MODEL_API_BASE_URL + "create", leagueId);
    }

    modelGenerated() {
        return axios.post(MODEL_API_BASE_URL + "request");
    }

    getScheduleComparison() {
        return axios.get(MODEL_API_BASE_URL + "compareSchedules");
    }

    getWeeklyComparison() {
        return axios.get(MODEL_API_BASE_URL + "compareWeekly");
    }

    getPlayoffRankings() {
        return axios.get(MODEL_API_BASE_URL + "playoffRankings");
    }

    getRemainingMatchupPeriods() {
        return axios.get(MODEL_API_BASE_URL + "remainingMatchupPeriods");
    }

    getPlayoffMachineMatchups() {
        return axios.get(MODEL_API_BASE_URL + "playoffMachineMatchups");
    }

    setWinnerHome(matchup) {
        return axios.post(MODEL_API_BASE_URL + "setWinnerHome", matchup);
    }

    setWinnerAway(matchup) {
        return axios.post(MODEL_API_BASE_URL + "setWinnerAway", matchup);
    }

    setWinnerTie(matchup) {
        return axios.post(MODEL_API_BASE_URL + "setWinnerTie", matchup)
    }
    getIsSorted() {
        return axios.get(MODEL_API_BASE_URL + "isSorted");
    }
}

export default new ModelService()