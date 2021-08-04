import { useRecoilState } from 'recoil';

import { newUserState } from '../../state/register';
import { requestRegister } from '../../request/register';
import { useHistory } from 'react-router-dom';
import useAccessToken from './useAccessToken';

const useRegister = () => {
  const history = useHistory();
  const [user, setUser] = useRecoilState(newUserState);

  const { storeAccessToken } = useAccessToken();
  const { pageName } = user;

  const resetRegister = () => {
    setUser({ email: '', nickname: '', oauthType: '', pageName: '' });
  };

  const registerUser = async () => {
    try {
      if (!(user.email && user.nickname && user.oauthType && user.pageName)) {
        throw Error('비정상적인 회원가입 절차입니다.');
      }
      const { token } = await requestRegister(user);

      storeAccessToken(token);

      history.push('/register/success');
    } catch (error) {
      history.push('/');
    }
  };

  return {
    pageName,
    resetRegister,
    registerUser,
  };
};

export default useRegister;
