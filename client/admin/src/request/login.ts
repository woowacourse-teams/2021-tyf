import { BASE_URL } from '../constants/api';

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
