import { apiClient } from '../../API';
import { IamportResponse } from '../../iamport';
import { Donation, Payment } from '../../types';

export const requestPayment = (paymentInfo: Payment): Promise<{ merchantUid: number }> => {
  return apiClient.post('/payments', paymentInfo);
};

export const requestPaymentComplete = ({
  imp_uid,
  merchant_uid,
}: IamportResponse): Promise<Donation> => {
  return apiClient.post('/donations', { impUid: imp_uid, merchantUid: merchant_uid });
};
