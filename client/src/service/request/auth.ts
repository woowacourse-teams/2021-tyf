import { apiClient } from '../../API';
import { LoginUserInfo, OAuthProvider } from '../../types';

export const requestLogin = (provider: OAuthProvider, authCode: string) => {
  return apiClient
    .get<{ token: string }>(`/oauth2/login/${provider}?code=${authCode}`)
    .then((response) => response.data);
};

export const requestLoginUserInfo = (accessToken: string) => {
  return apiClient
    .get<LoginUserInfo>('/members/me', {
      headers: { Authorization: `bearer ${accessToken}` },
    })
    .then((response) => response.data);
};
