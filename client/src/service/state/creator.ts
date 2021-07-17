import { selector, selectorFamily } from 'recoil';

import {
  requestCreator,
  requestCreatorList,
  requestCreatorPublicDonationList,
} from './../request/creator';
import { Creator, CreatorId, Donation } from './../../types';

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
