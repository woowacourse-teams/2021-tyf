import { useEffect } from 'react';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import { requestIdState } from '../state/request';

import { userStatisticsQuery } from '../state/statistics';
import useAccessToken from './useAccessToken';

const useStatistics = () => {
  const { accessToken } = useAccessToken();
  const { point: totalAmount } = useRecoilValue(userStatisticsQuery);
  const [requestId, setRequestId] = useRecoilState(requestIdState(accessToken));

  useEffect(() => {
    setRequestId(requestId + 1);
  }, []);

  return { totalAmount };
};

export default useStatistics;
