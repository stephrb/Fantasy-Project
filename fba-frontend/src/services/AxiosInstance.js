import axios from "axios";

const axiosInstance = axios.create();

axiosInstance.defaults.headers.common["leagueId"] =
  localStorage.getItem("leagueId");
axiosInstance.defaults.headers.common["userId"] =
  localStorage.getItem("userId");

axiosInstance.interceptors.request.use((config) => {
  config.headers["leagueId"] = localStorage.getItem("leagueId");
  config.headers["userId"] = localStorage.getItem("userId");
  return config;
});

export default axiosInstance;
