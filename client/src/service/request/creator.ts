import { Creator, CreatorId, Donation } from '../../types';
import { apiClient, authorizationHeader } from './../../API';

export const requestCreatorList = (): Promise<Creator[]> => {
  return apiClient.get('/members/curations');
};

export const requestCreator = (creatorId: CreatorId): Promise<Creator> => {
  return apiClient.get(`/members/${creatorId}`);
};

export const requestCreatorPrivateDonationList = (
  accessToken: string,
  page: number,
  size: number
): Promise<Donation[]> => {
  return apiClient.get(`/donations/me?page=${page}&size=${size}`, authorizationHeader(accessToken));
};

export const requestCreatorPublicDonationList = (creatorId: CreatorId): Promise<Donation[]> => {
  return apiClient.get(`/donations/public/${creatorId}`);
};
