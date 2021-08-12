import { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';

import {
  requestAgreeBankAccount,
  requestDeclineBankAccount,
  requestBankAccountList,
} from '../components/request/request';
import { BankAccount } from '../type';

const useBankAccount = () => {
  const history = useHistory();
  const [bankAccountList, setBankAccountList] = useState<BankAccount[]>([]);
  const [accessToken, setAccessToken] = useState('');

  // TODO: 분리하기
  const getAccessToken = () => {
    const token = sessionStorage.getItem('adminToken');
    if (!token) {
      alert('로그인 후 접근가능합니다.');
      sessionStorage.setItem('adminToken', '');
      history.push('/');
      return;
    }

    setAccessToken(token);
  };

  const getBankAccountList = async () => {
    try {
      const data: BankAccount[] = await requestBankAccountList(accessToken);

      setBankAccountList(data);
    } catch (error) {
      alert(error.message);
      if (error.errorCode === 'auth-002') {
        history.push('/');
      }
    }
  };

  const agreeBankAccount = async (userId: number) => {
    try {
      await requestAgreeBankAccount(userId, accessToken);

      alert('계좌 신청을 수락했습니다.');
    } catch (error) {
      alert(error.message);
    }
  };

  const declineBankAccount = async (userId: number) => {
    try {
      const reason = window.prompt('거절 사유를 적어주세요');
      if (!reason) {
        throw Error('거절 사유가 작성되지 않았습니다.');
      }

      await requestDeclineBankAccount(userId, reason, accessToken);

      alert('계좌 신청을 거절했습니다.');
    } catch (error) {
      alert(error.message);
    }
  };

  useEffect(() => {
    getAccessToken();
  }, []);

  useEffect(() => {
    if (!accessToken) return;

    getBankAccountList();
  }, [accessToken]);

  return { bankAccountList, agreeBankAccount, declineBankAccount };
};

export default useBankAccount;
