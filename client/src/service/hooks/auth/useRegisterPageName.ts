import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';

import { REGISTER } from '../../../constants/register';
import { debounceGenerator } from '../../../utils/debounce';
import { requestValidatePageName } from '../../request/register';
import { newUserState } from '../../state/register';

const pageNameValidation = async (pageName: string) => {
  if (pageName.length < REGISTER.ADDRESS.MIN_LENGTH) {
    return `주소는 최소 ${REGISTER.ADDRESS.MIN_LENGTH}글자 이상이여합니다.`;
  }

  if (REGISTER.ADDRESS.MAX_LENGTH < pageName.length) {
    return `주소는 최대 ${REGISTER.ADDRESS.MAX_LENGTH}글자 이하여야 합니다`;
  }

  if (pageName !== pageName.replace(/ /g, '')) {
    return '주소에는 공백이 존재하면 안됩니다.';
  }

  if (pageName !== pageName.toLowerCase()) {
    return '주소명은 소문자만 가능합니다.';
  }

  const regExp = /^[a-z0-9_\\-]*$/;
  if (!regExp.test(pageName)) {
    return "주소는 영어 소문자, 숫자, '-', '_' 만 가능합니다.";
  }

  try {
    await requestValidatePageName(pageName);

    return '';
  } catch (error) {
    return error.response.data.message;
  }
};

const debounce = debounceGenerator(300);

const useRegister = () => {
  const [user, setUser] = useRecoilState(newUserState);
  const [addressErrorMessage, setAddressErrorMessage] = useState('');
  const [isValidAddress, setIsValidAddress] = useState(false);

  const { pageName } = user;

  const validate = async () => {
    const addressError = await pageNameValidation(pageName);

    if (addressError) {
      setIsValidAddress(false);
      setAddressErrorMessage(addressError);
    } else {
      setIsValidAddress(true);
      setAddressErrorMessage('');
    }
  };

  useEffect(() => {
    setIsValidAddress(false);

    debounce(validate);
  }, [pageName]);

  const setPageName = (value: string) => {
    setUser({ ...user, pageName: value });
  };

  return {
    pageName,
    addressErrorMessage,
    isValidAddress,
    setPageName,
  };
};

export default useRegister;
