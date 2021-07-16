import { Creator } from '../../types';
import { apiClient } from './../../API';

export const requestCreatorList = (): Promise<Creator[]> => {
  return apiClient.get('creators').then((response) => response.data); // TODO 바꿔야됨
};
