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
import { SETTLEMENT_ACCOUNT_ERROR_MESSAGE, SETTLEMENT_ERROR_MESSAGE } from '../../constants/error';

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
      const { errorCode }: { errorCode: keyof typeof SETTLEMENT_ERROR_MESSAGE } =
        error.response.data;
      const errorMessage =
        SETTLEMENT_ERROR_MESSAGE[errorCode] ??
        '정산 신청에 실패했습니다. 문제가 지속되면 고객센터로 문의해주세요.';
      alert(errorMessage);
    }
  };

  const registerSettlementAccount = async (form: SettlementAccountForm) => {
    try {
      await requestRegisterSettlementAccount(form, accessToken);

      alert('계좌정보 등록에 전송되었습니다. 검토에는 2~3일정도 소요됩니다.');
      history.push('/');
    } catch (error) {
      const { errorCode }: { errorCode: keyof typeof SETTLEMENT_ACCOUNT_ERROR_MESSAGE } =
        error.response.data;
      const errorMessage =
        SETTLEMENT_ACCOUNT_ERROR_MESSAGE[errorCode] ??
        '계좌정보를 등록하는데 실패했습니다. 문제가 지속되면 고객센터로 문의해주세요.';
      alert(errorMessage);
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
