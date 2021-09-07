import { useState } from 'react';

import { MAX_DONATION_AMOUNT, MIN_DONATION_AMOUNT } from '../../constants/donation';

const useDonationAmountForm = () => {
  const [donationAmount, _setDonationAmount] = useState(0);

  const addDonationAmount = (amount: number) => {
    if (amount < 0) return;

    if (!donationAmount) {
      _setDonationAmount(amount);
      return;
    }

    setDonationAmount((donationAmount + amount).toString());
  };

  const setDonationAmount = (value: string) => {
    if (/[^0-9]/.test(value) || Number(value) >= MAX_DONATION_AMOUNT) return;

    _setDonationAmount(Number(value));
  };

  const isDonationAmountInValidRange =
    MIN_DONATION_AMOUNT <= donationAmount && donationAmount <= MAX_DONATION_AMOUNT;

  return { donationAmount, setDonationAmount, addDonationAmount, isDonationAmountInValidRange };
};

export default useDonationAmountForm;
