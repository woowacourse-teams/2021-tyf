import { useEffect, useState } from 'react';
import { useRecoilState, useRecoilValueLoadable } from 'recoil';
import { requestValidateNickName } from '../request/register';

import { newUserState, nickNameValidationSelector } from '../state/register';

let dbValidationTimer: NodeJS.Timer;
const useRegisterNickname = () => {
  const [user, setUser] = useRecoilState(newUserState);
  const nickNameErrorLoadable = useRecoilValueLoadable(nickNameValidationSelector);
  const [nicknameErrorMessage, setNicknameErrorMessage] = useState('');
  const [isValidNickName, setIsValidNickname] = useState(false);

  const { nickname } = user;

  useEffect(() => {
    if (nickNameErrorLoadable.state === 'loading') {
      clearTimeout(dbValidationTimer);
      setIsValidNickname(false);
      setNicknameErrorMessage('유효한 닉네임인지 검증중입니다...');
    }

    if (nickNameErrorLoadable.state === 'hasError') {
      setIsValidNickname(false);
      setNicknameErrorMessage(nickNameErrorLoadable.contents.response.data.message);
    }

    if (nickNameErrorLoadable.state === 'hasValue') {
      setIsValidNickname(false);
      setNicknameErrorMessage(nickNameErrorLoadable.contents);

      if (nickNameErrorLoadable.contents) return;

      dbValidationTimer = setTimeout(async () => {
        try {
          await requestValidateNickName(nickname);

          setIsValidNickname(true);
          setNicknameErrorMessage(nickNameErrorLoadable.contents);
        } catch (error) {
          setIsValidNickname(false);
          setNicknameErrorMessage(error.response.data.message);
        }
      }, 1000);
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
