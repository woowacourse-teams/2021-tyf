import { apiClient } from '../../API';
import { CreatorId, Donation, DonationId } from '../../types';

export const requestDonation = (
  creatorId: CreatorId,
  donationAmount: number
): Promise<Donation> => {
  return apiClient.post('/donations', { pageName: creatorId, donationAmount });
};

export const requestSendDonationMessage = (
  donationId: DonationId,
  name: string,
  message: string,
  secret: boolean
) => {
  return apiClient.post(`/donations/${donationId}/messages`, { name, message, secret });
};
