import axios, { AxiosInstance } from 'axios';

const baseURL =
  process.env.NODE_ENV === 'production'
    ? 'https://thank-you-for.kro.kr/'
    : 'https://thank-you-for-test.kro.kr/';

export const apiClient: AxiosInstance = axios.create({
  baseURL,
  headers: {
    'Content-Type': 'application/json; charset=utf-8',
  },
});

apiClient.interceptors.response.use((response) => response.data);

export const authorizationHeader = (accessToken: string) => ({
  headers: { Authorization: `bearer ${accessToken}` },
});
