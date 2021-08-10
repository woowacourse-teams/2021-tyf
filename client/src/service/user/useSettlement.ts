import { useEffect } from 'react';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import { requestIdState } from '../@state/request';
import { settlementQuery, settlementQueryKey } from '../@state/settlement';

const useSettlement = () => {
  const { donationAmount, settlableAmount, settledAmount } = useRecoilValue(settlementQuery);
  const setRequestId = useSetRecoilState(requestIdState(settlementQueryKey));

  useEffect(() => {
    setRequestId((prev) => prev + 1);
  }, []);

  const applySettlement = () => {};

  return { donationAmount, settlableAmount, settledAmount, applySettlement };
};

export default useSettlement;
