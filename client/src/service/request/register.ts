import { apiClient } from '../../API';
import { Register } from '../../types';
import { OAuthProvider } from '../../types';

export const requestOAuthRegister = (provider: OAuthProvider, authCode: string) => {
  return apiClient
    .get<{ email: string; oauthType: string }>(`/oauth2/signup/ready/${provider}?code=${authCode}`)
    .then((response) => response.data);
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

export const requestRegister = (user: Register) => {
  return apiClient.post('/oauth2/signup', user);
};
