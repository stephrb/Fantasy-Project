import axios from 'axios'

const MODEL_API_BASE_URL = "http://localhost:8080/api/v1/";

class ModelService {
    getTeams() {
        return axios.get(MODEL_API_BASE_URL + "teams");
    }

    createModel(leagueId) {
        return axios.post(MODEL_API_BASE_URL + "teams", leagueId);
    }

    modelGenerated() {
        return axios.post(MODEL_API_BASE_URL + "request")
    }
}

export default new ModelService()