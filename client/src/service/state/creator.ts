import { selector, selectorFamily } from 'recoil';

import {
  requestCreator,
  requestCreatorList,
  requestCreatorPrivateDonationList,
  requestCreatorPublicDonationList,
} from './../request/creator';
import { Creator, CreatorId, Donation } from './../../types';
import { accessTokenState } from './login';

export const creatorListQuery = selector<Creator[]>({
  key: 'creatorListQuery',
  get: () => requestCreatorList(),
});

export const creatorQuery = selectorFamily<Creator, CreatorId>({
  key: 'creatorQuery',
  get: (creatorId) => () => requestCreator(creatorId),
});

export const creatorPublicDonationListQuery = selectorFamily<Donation[], CreatorId>({
  key: 'creatorPublicDonationListQuery',
  get: (creatorId) => () => requestCreatorPublicDonationList(creatorId),
});

export const creatorPrivateDonationListQuery = selectorFamily<
  Donation[],
  { page: number; size: number }
>({
  key: 'creatorPrivateDonationListQuery',
  get:
    ({ page = 1, size = 5 }) =>
    ({ get }) => {
      const accessToken = get(accessTokenState);
      return requestCreatorPrivateDonationList(accessToken, page, size);
    },
});
