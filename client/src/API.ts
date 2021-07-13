import axios, { AxiosInstance } from 'axios';

const baseURL = '';

export const apiClient: AxiosInstance = axios.create({
  baseURL,
  headers: {
    'Content-Type': 'application/json; charset=utf-8',
  },
});

export const createAuthorizationHeader = () => {};
