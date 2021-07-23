import { useRecoilState, useRecoilValue, useRecoilValueLoadable } from 'recoil';

import {
  newUserState,
  nickNameValidationSelector,
  urlNameValidationSelector,
} from '../state/register';
import { requestRegister } from '../request/register';
import { useHistory } from 'react-router-dom';
import useAccessToken from './useAccessToken';
import { useEffect, useState } from 'react';

const useRegister = () => {
  const history = useHistory();
  const [user, setUser] = useRecoilState(newUserState);
  const addressErrorLoadable = useRecoilValueLoadable(urlNameValidationSelector);
  const nickNameErrorLoadable = useRecoilValueLoadable(nickNameValidationSelector);

  const [addressErrorMessage, setAddressErrorMessage] = useState('');
  const [nicknameErrorMessage, setNicknameErrorMessage] = useState('');
  const [isValidAddress, setIsValidAddress] = useState(false);
  const [isValidNickName, setIsValidNickname] = useState(false);

  const { storeAccessToken } = useAccessToken();

  const { pageName, nickname } = user;

  useEffect(() => {
    if (addressErrorLoadable.state === 'loading') {
      setIsValidAddress(false);
      setAddressErrorMessage('유효한 주소명인지 검증중입니다...');
    }

    if (addressErrorLoadable.state === 'hasError') {
      setIsValidAddress(false);
      setAddressErrorMessage(addressErrorLoadable.contents.response.data.message);
    }

    if (addressErrorLoadable.state === 'hasValue') {
      setIsValidAddress(!addressErrorLoadable.contents);
      setAddressErrorMessage(addressErrorLoadable.contents);
    }
  }, [addressErrorLoadable.state]);

  useEffect(() => {
    if (nickNameErrorLoadable.state === 'loading') {
      setIsValidNickname(false);
      setNicknameErrorMessage('유효한 닉네임인지 검증중입니다...');
    }

    if (nickNameErrorLoadable.state === 'hasError') {
      setIsValidNickname(false);
      setNicknameErrorMessage(nickNameErrorLoadable.contents.response.data.message);
    }

    if (nickNameErrorLoadable.state === 'hasValue') {
      setIsValidNickname(!nickNameErrorLoadable.contents);
      setNicknameErrorMessage(nickNameErrorLoadable.contents);
    }
  }, [nickNameErrorLoadable.state]);

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
    nicknameErrorMessage,
    isValidNickName,
    setNickname,
    setPageName,
    resetRegister,
    registerUser,
  };
};

export default useRegister;
