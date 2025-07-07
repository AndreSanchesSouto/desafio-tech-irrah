export const API_URL: string = "http://localhost:8080";
export const URL: string = 'http://localhost:5173';

export const getHeaders = () => ({
    headers: {
        'Authorization': `Bearer ${localStorage.getItem('access_token_api')}`,
        'Content-Type': 'application/json',
    },
    withCredentials: true
});