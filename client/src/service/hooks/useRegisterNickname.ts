import { useEffect, useState } from 'react';
import { useRecoilState, useRecoilValueLoadable } from 'recoil';

import { newUserState, nickNameValidationSelector } from '../state/register';

const useRegisterNickname = () => {
  const [user, setUser] = useRecoilState(newUserState);
  const nickNameErrorLoadable = useRecoilValueLoadable(nickNameValidationSelector);
  const [nicknameErrorMessage, setNicknameErrorMessage] = useState('');
  const [isValidNickName, setIsValidNickname] = useState(false);

  const { nickname } = user;

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

  return {
    nickname,
    nicknameErrorMessage,
    isValidNickName,
    setNickname,
  };
};

export default useRegisterNickname;
