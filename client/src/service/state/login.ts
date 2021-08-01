import { atom, selector } from 'recoil';

import { requestUserInfo } from '../request/user';
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

const getDefaultLoginPersistenceType = (): StorageType => {
  const isLoginPersist = getLocalStorageItem(STORAGE_KEY.IS_LOGIN_PERSIST);

  return isLoginPersist ? 'LOCAL' : 'SESSION';
};

export const loginPersistenceTypeState = atom<StorageType>({
  key: 'loginPersistenceState',
  default: getDefaultLoginPersistenceType(),
});

export const loginUserInfoQuery = selector({
  key: 'loginUserInfoQuery',
  get: ({ get }) => {
    const accessToken = get(accessTokenState);

    if (!accessToken) return null;

    return requestUserInfo(accessToken);
  },
});
