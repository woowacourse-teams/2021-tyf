import { apiClient } from '../../API';

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
