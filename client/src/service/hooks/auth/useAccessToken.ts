import { useRecoilState } from 'recoil';
import { STORAGE_KEY } from '../../../constants/storage';
import {
  getLocalStorageItem,
  setLocalStorageItem,
  setSessionStorageItem,
} from '../../../utils/storage';
import { accessTokenState } from '../../state/login';

const useAccessToken = () => {
  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);

  const clearAccessToken = () => {
    localStorage.removeItem(STORAGE_KEY.ACCESS_TOKEN);
    sessionStorage.removeItem(STORAGE_KEY.ACCESS_TOKEN);
    setAccessToken('');
  };

  const storeAccessToken = (accessToken: string) => {
    const isLoginPersist = getLocalStorageItem(STORAGE_KEY.IS_LOGIN_PERSIST);

    clearAccessToken();

    isLoginPersist
      ? setLocalStorageItem(STORAGE_KEY.ACCESS_TOKEN, accessToken)
      : setSessionStorageItem(STORAGE_KEY.ACCESS_TOKEN, accessToken);

    setAccessToken(accessToken);
  };

  return { accessToken, storeAccessToken, clearAccessToken };
};

export default useAccessToken;
