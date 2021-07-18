import { apiClient } from '../../API';
import { CreatorId, Donation, DonationId } from '../../types';

export const requestDonation = (creatorId: CreatorId, donationAmount: number) => {
  return apiClient
    .post<Donation>('/donations', { pageName: creatorId, donationAmount })
    .then((response) => response.data);
};

export const requestSendDonationMessage = (
  donationId: DonationId,
  name: string,
  message: string,
  secret: boolean
) => {
  return apiClient.post(`/donations/${donationId}/messages`, { name, message, secret });
};
