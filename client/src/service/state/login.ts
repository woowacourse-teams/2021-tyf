import { atom, selector } from 'recoil';

import { requestLoginUserInfo } from '../request/auth';
import { getLocalStorageItem, getSessionStorageItem } from '../../utils/storage';
import { STORAGE_KEY } from '../../constants/storage';
import { StorageType } from '../../types';

const getDefaultAccessToken = () => {
  const local = getLocalStorageItem(STORAGE_KEY.ACCESS_TOKEN);
  const session = getSessionStorageItem(STORAGE_KEY.ACCESS_TOKEN);

  if (local) return local;
  if (session) return session;
  return '';
};

export const accessTokenState = atom<string>({
  key: 'accessTokenState',
  default: getDefaultAccessToken(),
});

export const loginPersistenceTypeState = atom<StorageType>({
  key: 'loginPersistenceState',
  default: 'SESSION',
});

export const loginUserInfoQuery = selector({
  key: 'loginUserInfoQuery',
  get: ({ get }) => {
    const accessToken = get(accessTokenState);

    if (!accessToken) return;

    return requestLoginUserInfo(accessToken);
  },
});
