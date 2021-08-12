import { selector } from 'recoil';
import { Statistics } from '../../types';
import { requestUserStatistics } from '../@request/statistics';
import { accessTokenState } from './login';
import { requestIdState } from './request';

export const userStatisticsQueryKey = 'userStatisticsQuery';

export const userStatisticsQuery = selector<Statistics>({
  key: userStatisticsQueryKey,
  get: ({ get }) => {
    const accessToken = get(accessTokenState);

    get(requestIdState(userStatisticsQueryKey));

    return requestUserStatistics(accessToken);
  },
});
