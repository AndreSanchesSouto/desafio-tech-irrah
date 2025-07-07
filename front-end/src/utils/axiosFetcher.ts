import { useEffect } from 'react';
import { axiosFetcherConfiguration } from './api';

export default function useAxiosConfiguration() {
    const token = localStorage.getItem('access_token_api');

    useEffect(() => {
        const requestIntercept = axiosFetcherConfiguration.interceptors.request.use(
            config => {
                if (!config.headers['Authorization']) {
                    config.headers['Authorization'] = `Bearer ${token}`;
                }
                return config;
            },
            (error) => Promise.reject(error)
        );

        return () => {
            axiosFetcherConfiguration.interceptors.request.eject(requestIntercept);
        }

    })

    return async (url: string) => {
        const token = localStorage.getItem('access_token_api');

        axiosFetcherConfiguration.interceptors.response.use(
            response => response,
            async (error) => {
                if (error && 
                    error.response.status === 403 && 
                    error.response.data == ""
                ) {
                    window.location.href = "/auth";
                }
                return Promise.reject(error);
            });

        const response = await axiosFetcherConfiguration.get(url, {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        });
        return response.data;
    };
}