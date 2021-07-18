import { selector } from 'recoil';
import { requestUserStatistics } from '../request/statistics';
import { accessTokenState } from './login';

export const userStatisticsQuery = selector({
  key: 'userStatisticsQuery',
  get: ({ get }) => {
    const accessToken = get(accessTokenState);

    console.log(accessToken);

    return requestUserStatistics(accessToken);
  },
});
