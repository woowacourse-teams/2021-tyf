import { useEffect } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';
import { requestIdState } from '../../state/request';

import { userStatisticsQuery, userStatisticsQueryKey } from '../../state/statistics';

const useStatistics = () => {
  const { point: totalAmount } = useRecoilValue(userStatisticsQuery);
  const [requestId, setRequestId] = useRecoilState(requestIdState(userStatisticsQueryKey));

  useEffect(() => {
    setRequestId(requestId + 1);
  }, []);

  return { totalAmount };
};

export default useStatistics;
