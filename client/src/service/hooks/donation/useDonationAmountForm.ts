import { useState } from 'react';
import { useSetRecoilState } from 'recoil';
import { MAX_DONATION_AMOUNT, MIN_DONATION_AMOUNT } from '../../../constants/donation';
import { donationState } from '../../state/donation';

const useDonationAmountForm = () => {
  const [donationAmount, _setDonationAmount] = useState('');
  const setGlobalDonation = useSetRecoilState(donationState);

  const addDonationAmount = (amount: number) => {
    if (amount < 0) return;

    if (!donationAmount) _setDonationAmount(amount.toString());

    setDonationAmount((Number(donationAmount) + amount).toString());
  };

  const setDonationAmount = (value: string) => {
    if (/[^0-9]/.test(value) || Number(value) >= Number.MAX_SAFE_INTEGER) return;

    _setDonationAmount(value);
    setGlobalDonation((prev) => ({ ...prev, amount: Number(value) }));
  };

  const isDonationAmountInValidRange =
    MIN_DONATION_AMOUNT <= Number(donationAmount) && Number(donationAmount) <= MAX_DONATION_AMOUNT;

  return {
    donationAmount,
    setDonationAmount,
    addDonationAmount,
    isDonationAmountInValidRange,
  };
};

export default useDonationAmountForm;
