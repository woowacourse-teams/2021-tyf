import { useEffect } from 'react';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import { requestIdState } from '../@state/request';

import { userStatisticsQuery, userStatisticsQueryKey } from '../@state/statistics';

const useStatistics = () => {
  const { point: totalAmount } = useRecoilValue(userStatisticsQuery);
  const setRequestId = useSetRecoilState(requestIdState(userStatisticsQueryKey));

  useEffect(() => {
    setRequestId((prev) => prev + 1);
  }, []);

  return { totalAmount };
};

export default useStatistics;
