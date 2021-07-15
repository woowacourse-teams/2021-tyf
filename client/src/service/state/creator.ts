import { selector } from 'recoil';

import { creatorListMock } from './../../mock/mockData';
import { requestCreatorList } from './../request/creator';
import { Creator } from './../../types';

export const creatorListQuery = selector<Creator[]>({
  key: 'creatorListQuery',
  get: () => {
    //  return requestCreatorList();
    return creatorListMock;
  },
});
