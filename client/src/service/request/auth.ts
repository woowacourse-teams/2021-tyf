import { apiClient, authorizationHeader } from '../../API';
import { LoginUserInfo, OAuthProvider } from '../../types';

export const requestLogin = (
  provider: OAuthProvider,
  authCode: string
): Promise<{ token: string }> => {
  return apiClient.get(`/oauth2/login/${provider}?code=${authCode}`);
};

export const requestLoginUserInfo = (accessToken: string): Promise<LoginUserInfo> => {
  return apiClient.get('/members/me', authorizationHeader(accessToken));
};
