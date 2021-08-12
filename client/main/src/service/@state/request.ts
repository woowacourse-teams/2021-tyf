import { atomFamily } from 'recoil';

export const requestIdState = atomFamily({
  key: 'requestIdState',
  default: 0,
});
