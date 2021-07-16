import { atom, selector } from 'recoil';

import { requestLoginUserInfo } from '../request/auth';

const getDefaultAccessToken = () => {
  // TODO: localstorage, sessionStorage 적용
  return '';
};

export const accessTokenState = atom({
  key: 'accessTokenState',
  default: getDefaultAccessToken(),
});

export const loginUserInfoQuery = selector({
  key: 'loginUserInfoQuery',
  get: ({ get }) => {
    const accessToken = get(accessTokenState);

    if (!accessToken) return;

    return requestLoginUserInfo(accessToken);
  },
});
