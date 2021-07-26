import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useRecoilState, useRecoilValueLoadable } from 'recoil';
import { requestValidatePageName } from '../request/register';

import { newUserState, urlNameValidationSelector } from '../state/register';

let dbValidationTimer: NodeJS.Timer;
const useRegister = () => {
  const [user, setUser] = useRecoilState(newUserState);
  const addressErrorLoadable = useRecoilValueLoadable(urlNameValidationSelector);
  const [addressErrorMessage, setAddressErrorMessage] = useState('');
  const [isValidAddress, setIsValidAddress] = useState(false);

  const { pageName } = user;

  useEffect(() => {
    if (addressErrorLoadable.state === 'loading') {
      clearTimeout(dbValidationTimer);
      setIsValidAddress(false);
      setAddressErrorMessage('유효한 주소명인지 검증중입니다...');
    }

    if (addressErrorLoadable.state === 'hasError') {
      setIsValidAddress(false);
      setAddressErrorMessage(addressErrorLoadable.contents.response.data.message);
    }

    if (addressErrorLoadable.state === 'hasValue') {
      setIsValidAddress(false);
      setAddressErrorMessage(addressErrorLoadable.contents);

      if (addressErrorLoadable.contents) return;

      dbValidationTimer = setTimeout(async () => {
        try {
          await requestValidatePageName(pageName);

          setIsValidAddress(true);
          setAddressErrorMessage(addressErrorLoadable.contents);
        } catch (error) {
          setIsValidAddress(false);
          setAddressErrorMessage(error.response.data.message);
        }
      }, 1000);
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
