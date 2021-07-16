import { apiClient } from '../../API';
import { Register } from '../../types';

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
  return apiClient.post('/oauth2/signup', { user });
};
