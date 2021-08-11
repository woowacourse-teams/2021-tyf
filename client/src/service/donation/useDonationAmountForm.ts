import { useState } from 'react';

import { MAX_DONATION_AMOUNT, MIN_DONATION_AMOUNT } from '../../constants/donation';

const useDonationAmountForm = () => {
  const [donationAmount, _setDonationAmount] = useState('');

  const addDonationAmount = (amount: number) => {
    if (amount < 0) return;

    if (!donationAmount) {
      _setDonationAmount(amount.toString());
      return;
    }

    setDonationAmount((Number(donationAmount) + amount).toString());
  };

  const setDonationAmount = (value: string) => {
    if (/[^0-9]/.test(value) || Number(value) >= MAX_DONATION_AMOUNT) return;

    _setDonationAmount(value);
  };

  const isDonationAmountInValidRange =
    MIN_DONATION_AMOUNT <= Number(donationAmount) && Number(donationAmount) <= MAX_DONATION_AMOUNT;

  return { donationAmount, setDonationAmount, addDonationAmount, isDonationAmountInValidRange };
};

export default useDonationAmountForm;
