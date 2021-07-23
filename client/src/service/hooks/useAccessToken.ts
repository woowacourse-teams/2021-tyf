import { useRecoilState } from 'recoil';
import { STORAGE_KEY } from '../../constants/storage';
import { StorageType } from '../../types';
import {
  getLocalStorageItem,
  setLocalStorageItem,
  setSessionStorageItem,
} from '../../utils/storage';
import { accessTokenState } from '../state/login';

const useAccessToken = () => {
  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);

  const storeAccessToken = (accessToken: string) => {
    const isLoginPersist = getLocalStorageItem(STORAGE_KEY.IS_LOGIN_PERSIST);

    isLoginPersist
      ? setLocalStorageItem(STORAGE_KEY.ACCESS_TOKEN, accessToken)
      : setSessionStorageItem(STORAGE_KEY.ACCESS_TOKEN, accessToken);

    setAccessToken(accessToken);
  };

  return { accessToken, storeAccessToken };
};

export default useAccessToken;
