import { selector, selectorFamily } from 'recoil';

import { requestCreator, requestCreatorList } from './../request/creator';
import { Creator, CreatorId } from './../../types';

export const creatorListQuery = selector<Creator[]>({
  key: 'creatorListQuery',
  get: () => requestCreatorList(),
});

export const creatorQuery = selectorFamily<Creator, CreatorId>({
  key: 'creatorQuery',
  get: (creatorId) => () => requestCreator(creatorId),
});
