import { useRecoilState } from 'recoil';
import { useHistory } from 'react-router-dom';

import { newUserState } from '../../state/register';
import { requestRegister } from '../../request/register';
import useAccessToken from './useAccessToken';

const useRegister = () => {
  const history = useHistory();
  const [user, setUser] = useRecoilState(newUserState);
  const { storeAccessToken } = useAccessToken();

  const resetRegister = () => {
    setUser({ email: '', nickname: '', oauthType: '', pageName: '' });
  };

  const registerUser = async () => {
    try {
      const { email, nickname, oauthType, pageName } = user;

      if (!(email && nickname && oauthType && pageName)) {
        throw Error('비정상적인 회원가입 절차입니다.');
      }

      const { token } = await requestRegister(user);

      storeAccessToken(token);

      history.push('/register/success');
    } catch (error) {
      history.push('/');
    }
  };

  return { user, resetRegister, registerUser };
};

export default useRegister;
