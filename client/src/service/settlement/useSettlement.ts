import { useEffect } from 'react';
import { useHistory } from 'react-router';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import { SettlementAccountForm } from '../../types';
import { requestRegisterSettlementAccount, requestSettlement } from '../@request/settlement';

import { requestIdState } from '../@state/request';
import {
  settlementPointQuery,
  settlementQueryKey,
  settlementAccountQuery,
} from '../@state/settlement';
import useAccessToken from '../@shared/useAccessToken';

const useSettlement = () => {
  const { accessToken } = useAccessToken();
  const { currentPoint, exchangeablePoint, exchangedTotalPoint } =
    useRecoilValue(settlementPointQuery);
  const account = useRecoilValue(settlementAccountQuery);
  const setRequestId = useSetRecoilState(requestIdState(settlementQueryKey));
  const history = useHistory();

  useEffect(() => setRequestId((prev) => prev + 1), []);

  const applySettlement = async () => {
    try {
      await requestSettlement(accessToken);
      alert('정산신청이 완료됐습니다.');
      history.push('/');
    } catch (error) {
      alert('등록에 실패했습니다');
    }
  };

  const registerSettlementAccount = async (form: SettlementAccountForm) => {
    try {
      await requestRegisterSettlementAccount(form, accessToken);

      alert('계정정보 등록에 성공했습니다!');
      history.push('/');
    } catch (error) {
      alert('계좌정보를 등록하는데 실패했습니다.');
    }
  };

  return {
    account,
    currentPoint,
    exchangeablePoint,
    exchangedTotalPoint,
    applySettlement,
    registerSettlementAccount,
  };
};

export default useSettlement;
