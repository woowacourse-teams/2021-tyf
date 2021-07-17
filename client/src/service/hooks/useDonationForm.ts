import { useState } from 'react';

const MIN_DONATION_AMOUNT = 1000;

const useDonationForm = () => {
  const [donationAmount, setDonationAmount] = useState(0);

  const addDonationAmount = (amount: number) => {
    if (amount < 0) return;

    setDonationAmount(donationAmount + amount);
  };

  const isDonationAmountInValidRange = MIN_DONATION_AMOUNT <= donationAmount;

  return { donationAmount, setDonationAmount, addDonationAmount, isDonationAmountInValidRange };
};

export default useDonationForm;

// NOTE: 비제어 컴포넌트 버전

// const donationAmountInputRef = useRef<HTMLInputElement>(null);
// const [isDonationAmountInValidRange, setIsDonationAmountInValidRange] =

// const addDonationAmount = (amount: number) => {
//   if (!donationAmountInputRef.current || amount < 0) return;

//   if (!donationAmountInputRef.current.value) {
//     donationAmountInputRef.current.valueAsNumber = 0;
//   }

//   donationAmountInputRef.current.valueAsNumber += amount;
// };

// const isDonationAmountInValidRange = () => {
//   if (!donationAmountInputRef.current) return false;

//   return 1000 <= donationAmountInputRef.current.valueAsNumber;
// };

// return {
//   donationAmountInputRef,
//   addDonationAmount,
//   isDonationAmountInValidRange,
// };
