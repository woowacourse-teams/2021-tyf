import { BASE_URL } from '../../constants/api';

export const requestLogin = async (id: string, password: string) => {
  const response = await fetch(BASE_URL + '/admin/login', {
    method: 'POST',
    body: JSON.stringify({
      id,
      password,
    }),
    headers: {
      'Content-Type': 'application/json; charset=utf-8',
    },
  });

  if (response.ok) {
    return response.json();
  }

  throw Error('로그인에 실패했습니다!');
};

export const requestExchangeList = async (accessToken: string) => {
  const response = await fetch(BASE_URL + '/admin/list/exchange', {
    headers: {
      Authorization: `bearer ${accessToken}`,
    },
  });

  if (response.ok) {
    return response.json();
  }

  throw Error('정산 신청목록을 불러오는데 실패했습니다!');
};

export const requestAgreeExchange = async (pageName: string, accessToken: string) => {
  const response = await fetch(BASE_URL + '/admin/exchange/approve', {
    method: 'POST',
    body: JSON.stringify({
      pageName,
    }),
    headers: {
      Authorization: `bearer ${accessToken}`,
    },
  });

  if (response.ok) return;

  throw Error('정산수락에 실패했습니다!');
};

export const requestDeclineExchange = async (
  pageName: string,
  reason: string,
  accessToken: string
) => {
  const response = await fetch(BASE_URL + '/admin/exchange/reject', {
    method: 'POST',
    body: JSON.stringify({
      pageName,
      reason,
    }),
    headers: {
      Authorization: `bearer ${accessToken}`,
    },
  });

  if (response.ok) return;

  throw Error('정산거절에 실패했습니다!');
};
