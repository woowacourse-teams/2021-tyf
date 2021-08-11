import { apiClient, authorizationHeader } from '../../API';
import { Point, SettlementAccount, SettlementAccountForm } from '../../types';

export const requestSettlementAccount = (accessToken: string): Promise<SettlementAccount> => {
  return apiClient.get('/members/me/account', authorizationHeader(accessToken));
};

export const requestRegisterSettlementAccount = (
  form: NonNullable<SettlementAccountForm>,
  accessToken: string
) => {
  return apiClient.post('/members/me/account', { form }, authorizationHeader(accessToken));
};

export const requestPointDetail = (accessToken: string): Promise<Point> => {
  return apiClient.get('/members/me/detailedPoint', authorizationHeader(accessToken));
};

export const requestSettlement = (accessToken: string) => {
  return apiClient.post('/members/me/exchange', authorizationHeader(accessToken));
};
