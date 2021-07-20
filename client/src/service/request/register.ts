import { apiClient } from '../../API';
import { Register } from '../../types';
import { OAuthProvider } from '../../types';

export const requestOAuthRegister = (
  provider: OAuthProvider,
  authCode: string
): Promise<Register> => {
  return apiClient.get(`/oauth2/signup/ready/${provider}?code=${authCode}`);
};

export const requestValidatePageName = (urlName: string) => {
  return apiClient.post('/members/validate/pageName', {
    pageName: urlName,
  });
};

export const requestValidateNickName = (nickName: string) => {
  return apiClient.post('/members/validate/nickname', {
    nickname: nickName,
  });
};

export const requestRegister = (user: Register): Promise<{ token: string }> => {
  return apiClient.post('/oauth2/signup', user);
};
