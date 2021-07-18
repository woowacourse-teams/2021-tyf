import { atom } from 'recoil';
import { Donation } from '../../types';

export const donationState = atom<Donation | null>({
  key: 'donationState',
  default: null,
});
