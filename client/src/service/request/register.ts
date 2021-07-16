import { apiClient } from '../../API';

export const requestValidatePageName = (urlName: string) => {
  return apiClient.post('/members/validate/pageName', {
    pageName: urlName,
  });
};
