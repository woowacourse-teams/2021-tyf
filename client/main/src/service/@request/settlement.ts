import { apiClient, authorizationHeader } from '../../API';
import { Point, SettlementAccount, SettlementAccountForm } from '../../types';

export const requestSettlementAccount = (accessToken: string): Promise<SettlementAccount> => {
  return apiClient.get('/members/me/account', authorizationHeader(accessToken));
};

export const requestRegisterSettlementAccount = (
  form: NonNullable<SettlementAccountForm>,
  accessToken: string
) => {
  const formData = new FormData();

  formData.append('bankbookImage', form.bankbookImage!, form.bankbookImage!.name);
  formData.append('accountHolder', form.accountHolder);
  formData.append('accountNumber', form.accountNumber);
  formData.append('residentRegistrationNumber', form.residentRegistrationNumber.join('-'));
  formData.append('bank', form.bank!);

  return apiClient.post('/members/me/account', formData, authorizationHeader(accessToken));
};

export const requestPointDetail = (accessToken: string): Promise<Point> => {
  return apiClient.get('/members/me/detailedPoint', authorizationHeader(accessToken));
};

export const requestSettlement = (accessToken: string) => {
  return apiClient.post('/members/me/exchange', {}, authorizationHeader(accessToken));
};
