import { Creator, CreatorId, Donation } from '../../types';
import { apiClient } from './../../API';

export const requestCreatorList = (): Promise<Creator[]> => {
  return apiClient.get('/members/curations').then((response) => response.data);
};

export const requestCreator = (creatorId: CreatorId): Promise<Creator> => {
  return apiClient.get(`/members/${creatorId}`).then((response) => response.data);
};

export const requestCreatorPrivateDonationList = (
  accessToken: string,
  page: number,
  size: number
): Promise<Donation[]> => {
  return apiClient
    .get(`/donations/me?page=${page}&size=${size}`, {
      headers: { Authorization: `bearer ${accessToken}` },
    })
    .then((response) => response.data);
};

export const requestCreatorPublicDonationList = (creatorId: CreatorId): Promise<Donation[]> => {
  return apiClient.get(`/donations/public/${creatorId}`).then((response) => response.data);
};
