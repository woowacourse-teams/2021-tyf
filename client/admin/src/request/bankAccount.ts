import { BASE_URL } from '../constants/api';

export const requestBankAccountList = async (accessToken: string) => {
  const response = await fetch(BASE_URL + '/admin/list/account', {
    headers: {
      Authorization: `bearer ${accessToken}`,
    },
  });

  if (response.ok) {
    return response.json();
  }

  return response.text().then((error) => {
    throw JSON.parse(error);
  });
};

export const requestAgreeBankAccount = async (userId: number, accessToken: string) => {
  const response = await fetch(BASE_URL + `/admin/account/approve/${userId}`, {
    method: 'POST',
    headers: {
      Authorization: `bearer ${accessToken}`,
    },
  });

  if (response.ok) return;

  throw Error('계좌 수락에 실패했습니다!');
};

export const requestDeclineBankAccount = async (
  userId: number,
  rejectReason: string,
  accessToken: string
) => {
  const response = await fetch(BASE_URL + `/admin/account/reject/${userId}`, {
    method: 'POST',
    body: JSON.stringify({
      rejectReason,
    }),
    headers: {
      'Authorization': `bearer ${accessToken}`,
      'Content-Type': 'application/json; charset=utf-8',
    },
  });

  if (response.ok) return;

  throw Error('계좌 거절에 실패했습니다!');
};
