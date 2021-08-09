import { selector } from 'recoil';
import { accessTokenState } from './login';
import { requestIdState } from './request';

export const settlementQueryKey = 'settlementQuery';

export const settlementQuery = selector({
  key: settlementQueryKey,
  get: ({ get }) => {
    get(accessTokenState);
    get(requestIdState(settlementQueryKey));

    // TODO: api 스키마 확정되면 api request함수 작성
    return Promise.resolve({
      donationAmount: 52000,
      settlableAmount: 90000,
      settledAmount: 800000,
    });
  },
});
