import { atom } from 'recoil';
import { Donation } from '../../types';

export const INVALID_DONATION_ID = -1;

export const donationState = atom<Donation>({
  key: 'donationState',
  default: {
    donationId: INVALID_DONATION_ID,
    email: '',
    name: '',
    message: '',
    amount: -1,
    createdAt: new Date(),
  },
});
