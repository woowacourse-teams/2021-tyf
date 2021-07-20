import { useRecoilState, useRecoilValue } from 'recoil';

import {
  newUserState,
  nickNameValidationSelector,
  urlNameValidationSelector,
} from '../state/register';
import { requestRegister } from '../request/register';
import { useHistory } from 'react-router-dom';
import useAccessToken from './useAccessToken';

const useRegister = () => {
  const history = useHistory();
  const [user, setUser] = useRecoilState(newUserState);
  const addressErrorMessage = useRecoilValue(urlNameValidationSelector);
  const nickNameErrorMessage = useRecoilValue(nickNameValidationSelector);
  const { storeAccessToken } = useAccessToken();

  // TODO: db로의 검증
  // const addressDBErrorMessage = useRecoilValueLoadable(urlNameDBValidationQuery);
  // const nickNameDBErrorMessage = useRecoilValueLoadable(nickNameDBValidationQuery);

  const { pageName, nickname } = user;

  const isValidAddress = !addressErrorMessage;
  const isValidNickName = !nickNameErrorMessage;

  const setNickname = (value: string) => {
    setUser({ ...user, nickname: value });
  };

  const setPageName = (value: string) => {
    setUser({ ...user, pageName: value });
  };

  const resetRegister = () => {
    setUser({ email: '', nickname: '', oauthType: '', pageName: '' });
  };

  const registerUser = async () => {
    try {
      if (!(user.email && user.nickname && user.oauthType && user.pageName)) {
        throw Error('비정상적인 회원가입 절차입니다.');
      }
      const { token } = await requestRegister(user);

      storeAccessToken(token, 'SESSION');

      history.push('/register/success');
    } catch (error) {
      history.push('/');
    }
  };

  return {
    pageName,
    addressErrorMessage,
    isValidAddress,
    nickname,
    nickNameErrorMessage,
    isValidNickName,
    setNickname,
    setPageName,
    resetRegister,
    registerUser,
  };
};

export default useRegister;
