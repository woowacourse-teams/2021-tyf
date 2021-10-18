import { useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { STORAGE_KEY } from '../constants/storage';

import { requestLogin } from '../request/login';
import { LoginResponse } from '../type';
import { setLocalStorageItem } from '../utils/storage';
import { accessTokenState } from './@state/login';

const useLogin = () => {
  const history = useHistory();
  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);

  const login = async (id: string, pwd: string) => {
    try {
      const { token }: LoginResponse = await requestLogin(id, pwd);

      setAccessToken(token);
      setLocalStorageItem(STORAGE_KEY.ACCESS_TOKEN, token);

      history.push('/bankAccount');
    } catch (error) {
      alert(error.message ?? error.data.message);
    }
  };

  useEffect(() => {
    if (!accessToken) return;

    localStorage.removeItem(STORAGE_KEY.ACCESS_TOKEN);
    setAccessToken('');
  }, []);

  return { accessToken, login };
};

export default useLogin;
