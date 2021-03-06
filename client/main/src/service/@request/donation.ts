import { apiClient, authorizationHeader } from '../../API';
import { CreatorId, Donation, DonationId } from '../../types';

export const requestDonation = (
  creatorId: CreatorId,
  point: number,
  accessToken: string
): Promise<Donation> => {
  return apiClient.post(
    '/donations',
    { pageName: creatorId, point },
    authorizationHeader(accessToken)
  );
};

export const requestSendDonationMessage = (
  donationId: DonationId,
  message: string,
  secret: boolean,
  accessToken: string
) => {
  return apiClient.post(
    `/donations/${donationId}/messages`,
    { message, secret },
    authorizationHeader(accessToken)
  );
};
