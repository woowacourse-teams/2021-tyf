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

// 정산
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
  const response = await fetch(BASE_URL + `/admin/exchange/approve/${pageName}`, {
    method: 'POST',
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
      'Authorization': `bearer ${accessToken}`,
      'Content-Type': 'application/json; charset=utf-8',
    },
  });

  if (response.ok) return;

  throw Error('정산거절에 실패했습니다!');
};

// 계좌
export const requestBankAccountList = async (accessToken: string) => {
  const response = await fetch(BASE_URL + '/admin/list/account', {
    headers: {
      Authorization: `bearer ${accessToken}`,
    },
  });

  if (response.ok) {
    return response.json();
  }

  throw Error('계좌 신청목록을 불러오는데 실패했습니다!');
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
