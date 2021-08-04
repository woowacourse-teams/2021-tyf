import { useHistory } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { STORAGE_KEY } from '../../../constants/storage';
import { accessTokenState } from '../../state/login';

const useLogout = () => {
  const setAccessToken = useSetRecoilState(accessTokenState);
  const history = useHistory();

  const logout = () => {
    setAccessToken('');
    sessionStorage.removeItem(STORAGE_KEY.ACCESS_TOKEN);
    localStorage.removeItem(STORAGE_KEY.ACCESS_TOKEN);

    history.push('/');
  };

  return { logout };
};

export default useLogout;
