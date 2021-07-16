import { useHistory } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { accessTokenState } from '../state/login';

const useLogout = () => {
  const setAccessToken = useSetRecoilState(accessTokenState);
  const history = useHistory();

  const logout = () => {
    setAccessToken('');
    history.push('/');
  };

  return { logout };
};

export default useLogout;
