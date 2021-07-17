import { apiClient } from '../../API';
import { CreatorId, Donation } from '../../types';

export const requestDonation = (creatorId: CreatorId, donationAmount: number) => {
  return apiClient
    .post<Donation>('/donations', { pageName: creatorId, donationAmount })
    .then((response) => response.data);
};
