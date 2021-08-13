import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';

import { REGISTER } from '../../constants/register';
import { debounceGenerator } from '../../utils/debounce';
import { requestValidateNickName } from '../@request/register';
import { newUserState } from '../@state/register';

const nicknameValidation = async (nickname: string) => {
  if (nickname.length < REGISTER.NICKNAME.MIN_LENGTH) {
    return `닉네임은 최소 ${REGISTER.NICKNAME.MIN_LENGTH}글자 이상이여합니다.`;
  }

  if (REGISTER.NICKNAME.MAX_LENGTH < nickname.length) {
    return `닉네임은 최대 ${REGISTER.NICKNAME.MAX_LENGTH}글자 이하여야 합니다`;
  }

  if (nickname !== nickname.trim()) {
    return '닉네임의 맨 앞과 뒤에는 공백이 불가능합니다.';
  }

  if (nickname !== nickname.replace(/ +/g, ' ')) {
    return '닉네임 사이에 공백은 한칸만 가능합니다.';
  }

  // 한글, 영어, 한자, 일본어, 숫자 매칭
  const regExp = /^[a-zA-Z0-9가-힇ㄱ-ㅎㅏ-ㅣぁ-ゔァ-ヴー々〆〤一-龥]*$/;
  if (!regExp.test(nickname)) {
    return '닉네임은 한글, 영어, 한자, 일본어, 숫자만 가능합니다.';
  }

  try {
    await requestValidateNickName(nickname);

    return '';
  } catch (error) {
    return error.response.data.message;
  }
};

const debounce = debounceGenerator(300);

const useRegisterNickname = () => {
  const [user, setUser] = useRecoilState(newUserState);
  const [nicknameErrorMessage, setNicknameErrorMessage] = useState('');
  const [isValidNickName, setIsValidNickname] = useState(false);

  const { nickname } = user;

  const validate = async () => {
    const nicknameError = await nicknameValidation(nickname);

    if (nicknameError) {
      setIsValidNickname(false);
      setNicknameErrorMessage(nicknameError);
    } else {
      setIsValidNickname(true);
      setNicknameErrorMessage('');
    }
  };

  useEffect(() => {
    setIsValidNickname(false);

    debounce(validate);
  }, [nickname]);

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
