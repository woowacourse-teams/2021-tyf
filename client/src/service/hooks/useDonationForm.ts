import { useState } from 'react';
import { MIN_DONATION_AMOUNT } from '../../constants/donation';

const useDonationForm = () => {
  const [donationAmount, _setDonationAmount] = useState('');

  const addDonationAmount = (amount: number) => {
    if (amount < 0) return;

    if (!donationAmount) _setDonationAmount(amount.toString());

    setDonationAmount((Number(donationAmount) + amount).toString());
  };

  const setDonationAmount = (value: string) => {
    if (/[^0-9]/.test(value) || Number(value) >= Number.MAX_SAFE_INTEGER) return;

    _setDonationAmount(value);
  };

  const isDonationAmountInValidRange = MIN_DONATION_AMOUNT <= Number(donationAmount);

  return {
    donationAmount,
    setDonationAmount,
    addDonationAmount,
    isDonationAmountInValidRange,
  };
};

export default useDonationForm;
