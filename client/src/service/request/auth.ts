import { apiClient } from '../../API';
import { OAuthProvider } from '../../types';

export const requestLogin = (provider: OAuthProvider, authCode: string): Promise<string> => {
  return apiClient.get(`/oauth2/login/${provider}?code=${authCode}`);
};

export const requestLoginUserInfo = (accessToken: string) => {
  return apiClient.get('/members/me', {
    headers: { Authorization: `bearer ${accessToken}` },
  });
};
