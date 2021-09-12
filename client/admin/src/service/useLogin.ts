import { useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import { useRecoilState } from 'recoil';

import { requestLogin } from '../request/login';
import { LoginResponse } from '../type';
import { accessTokenState } from './@state/login';

const useLogin = () => {
  const history = useHistory();
  const [accessToken, setAccessToken] = useRecoilState(accessTokenState);

  const login = async (id: string, pwd: string) => {
    try {
      const { token }: LoginResponse = await requestLogin(id, pwd);

      setAccessToken(token);

      history.push('/bankAccount');
    } catch (error) {
      alert(error.message ?? error.data.message);
    }
  };

  useEffect(() => {
    if (!accessToken) return;

    setAccessToken('');
  }, []);

  return { login };
};

export default useLogin;
