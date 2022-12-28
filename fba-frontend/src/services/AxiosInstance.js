import axios from 'axios';

const axiosInstance = axios.create();

axiosInstance.defaults.headers.common['leagueId'] = localStorage.getItem('leagueId');

axiosInstance.interceptors.request.use((config) => {
  config.headers['leagueId'] = localStorage.getItem('leagueId');
  return config;
});

export default axiosInstance;