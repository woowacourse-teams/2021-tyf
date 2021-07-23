import { useEffect, useState } from 'react';
import { useRecoilState, useRecoilValueLoadable } from 'recoil';

import { newUserState, urlNameValidationSelector } from '../state/register';

const useRegister = () => {
  const [user, setUser] = useRecoilState(newUserState);
  const addressErrorLoadable = useRecoilValueLoadable(urlNameValidationSelector);
  const [addressErrorMessage, setAddressErrorMessage] = useState('');
  const [isValidAddress, setIsValidAddress] = useState(false);

  const { pageName } = user;

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
