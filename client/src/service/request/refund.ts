import { apiClient, authorizationHeader } from '../../API';
import { Refund, refundOrderDetail } from '../../types';

export const requestVerifyMerchantUid = (merchantUid: string): Promise<Refund> => {
  return apiClient.post('/payments/refund/verification/ready', { merchantUid });
};

export const requestVerify = (
  merchantUid: string,
  verificationCode: string
): Promise<{ refundAccessToken: string }> => {
  return apiClient.post('/payments/refund/verification', { merchantUid, verificationCode });
};

export const requestRefundOrderInfo = (refundAccessToken: string): Promise<refundOrderDetail> => {
  return apiClient.post('/payments/refund/info', authorizationHeader(refundAccessToken));
};

export const requestRefund = (refundAccessToken: string) => {
  return apiClient.post('/payments/refund', authorizationHeader(refundAccessToken));
};
