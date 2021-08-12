import { apiClient, authorizationHeader } from '../../API';
import { Statistics } from '../../types';

export const requestUserStatistics = (accessToken: string): Promise<Statistics> => {
  return apiClient.get('/members/me/point', authorizationHeader(accessToken));
};
