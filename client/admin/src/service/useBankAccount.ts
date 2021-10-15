import { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import {
  requestAgreeBankAccount,
  requestDeclineBankAccount,
  requestBankAccountList,
} from '../request/bankAccount';
import { BankAccount } from '../type';
import { accessTokenState } from './@state/login';

const useBankAccount = () => {
  const history = useHistory();
  const [bankAccountList, setBankAccountList] = useState<BankAccount[]>([]);
  const accessToken = useRecoilValue(accessTokenState);

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
      location.reload();
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
      location.reload();
    } catch (error) {
      alert(error.message);
    }
  };

  useEffect(() => {
    getBankAccountList();
  }, []);

  return { bankAccountList, agreeBankAccount, declineBankAccount };
};

export default useBankAccount;
