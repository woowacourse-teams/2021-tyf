import { useRecoilValue } from 'recoil';

import { userStatisticsQuery } from '../state/statistics';

const useStatistics = () => {
  const { point: totalAmount } = useRecoilValue(userStatisticsQuery);

  return { totalAmount };
};

export default useStatistics;
