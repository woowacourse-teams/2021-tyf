import { useRecoilState } from 'recoil';
import { STORAGE_KEY } from '../../constants/storage';
import { StorageType } from '../../types';
import { setLocalStorageItem, setSessionStorageItem } from '../../utils/storage';
import { accessTokenState } from '../state/login';

const useAccessToken = () => {
  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);

  const storeAccessToken = (accessToken: string, storageType: StorageType) => {
    if (storageType === 'LOCAL') {
      setLocalStorageItem(STORAGE_KEY.ACCESS_TOKEN, accessToken);
    }

    if (storageType === 'SESSION') {
      setSessionStorageItem(STORAGE_KEY.ACCESS_TOKEN, accessToken);
    }

    setAccessToken(accessToken);
  };

  return { accessToken, storeAccessToken };
};

export default useAccessToken;
