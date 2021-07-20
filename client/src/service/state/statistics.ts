import { selector } from 'recoil';
import { Statistics } from '../../types';
import { requestUserStatistics } from '../request/statistics';
import { accessTokenState } from './login';

export const userStatisticsQuery = selector<Statistics>({
  key: 'userStatisticsQuery',
  get: ({ get }) => {
    const accessToken = get(accessTokenState);

    return requestUserStatistics(accessToken);
  },
});
