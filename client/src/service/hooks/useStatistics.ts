import { useRecoilValue } from 'recoil';

import { userStatisticsQuery } from '../state/statistics';

const useStatistics = () => {
  const { point } = useRecoilValue(userStatisticsQuery);

  return { point };
};

export default useStatistics;
