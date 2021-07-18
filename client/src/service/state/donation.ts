import { atom } from 'recoil';
import { Donation } from '../../types';

export const donationState = atom<Donation>({
  key: 'donationState',
  default: {
    donationId: -1,
    name: '',
    message: '',
    amount: -1,
    createdAt: new Date(),
  },
});
