import { apiClient } from '../../API';
import { Statistics } from '../../types';

export const requestUserStatistics = (accessToken: string) => {
  return apiClient
    .get<Statistics>('/members/me/point', {
      headers: {
        Authorization: `bearer ${accessToken}`,
      },
    })
    .then((response) => response.data);
};
