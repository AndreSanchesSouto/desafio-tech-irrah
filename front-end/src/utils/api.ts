import axios from 'axios';
import { API_URL } from './configs';

const api = axios.create({
    baseURL: API_URL,
    withCredentials: true
});

export const axiosFetcherConfiguration = axios.create({
    baseURL: API_URL,
    headers: { 'Content-Type': 'application/json' },
    withCredentials: true
});

export default api;