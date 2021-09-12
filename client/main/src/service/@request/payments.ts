import { apiClient, authorizationHeader } from '../../API';
import { IamportResponse } from '../../iamport';
import { Donation } from '../../types';

export const requestPayment = (
  itemId: string,
  accessToken: string
): Promise<{ merchantUid: string; itemName: string; amount: number; email: string }> => {
  return apiClient.post(
    '/payments/charge/ready',
    { itemId: itemId },
    authorizationHeader(accessToken)
  );
};

export const requestPaymentComplete = (
  { imp_uid, merchant_uid }: IamportResponse,
  accessToken: string
): Promise<Donation> => {
  return apiClient.post(
    '/payments/charge',
    { impUid: imp_uid, merchantUid: merchant_uid },
    authorizationHeader(accessToken)
  );
};
