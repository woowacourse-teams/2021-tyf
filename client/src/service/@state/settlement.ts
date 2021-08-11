import { selector } from 'recoil';
import { requestPointDetail, requestSettlementAccount } from '../@request/settlement';
import { accessTokenState } from './login';
import { requestIdState } from './request';

export const settlementQueryKey = 'settlementPointQuery';

export const settlementPointQuery = selector({
  key: settlementQueryKey,
  get: ({ get }) => {
    get(requestIdState(settlementQueryKey));

    const accessToken = get(accessTokenState);

    return requestPointDetail(accessToken);
  },
});

export const settlementAccountQuery = selector({
  key: 'settlementAccountQueryKey',
  get: ({ get }) => {
    get(requestIdState(settlementQueryKey));

    const accessToken = get(accessTokenState);

    return requestSettlementAccount(accessToken);
  },
});
