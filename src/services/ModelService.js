import axios from 'axios'

const MODEL_API_BASE_URL = "http://localhost:8080/api/v1/";

class ModelService {
    getPowerRankings() {
        return axios.get(MODEL_API_BASE_URL + "rankings");
    }

    createModel(leagueId) {
        return axios.post(MODEL_API_BASE_URL + "teams", leagueId);
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
}

export default new ModelService()