import { apiClient, authorizationHeader } from '../../API';
import { OAuthProvider } from '../../types';

export const requestLogin = (
  provider: OAuthProvider,
  authCode: string
): Promise<{ token: string }> => {
  return apiClient.get(`/oauth2/login/${provider}?code=${authCode}`);
};
