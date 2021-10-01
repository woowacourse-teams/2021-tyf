import { useRecoilState } from 'recoil';
import { useHistory } from 'react-router-dom';

import { newUserState } from '../@state/register';
import { requestRegister } from '../@request/register';
import useAccessToken from '../@shared/useAccessToken';

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
        throw Error();
      }

      const { token } = await requestRegister(user);

      storeAccessToken(token);

      history.push('/register/success');
    } catch (error) {
      alert('비정상적인 회원가입 절차입니다. 잠시후 다시 시도해주세요.');
      history.push('/');
    }
  };

  return { user, resetRegister, registerUser };
};

export default useRegister;
