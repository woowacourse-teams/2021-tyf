import { Creator, CreatorId } from '../../types';
import { apiClient } from './../../API';

export const requestCreatorList = (): Promise<Creator[]> => {
  return apiClient.get('/curations').then((response) => response.data);
};

export const requestCreator = (creatorId: CreatorId): Promise<Creator> => {
  return apiClient.get(`/members/${creatorId}`).then((response) => response.data);
};
