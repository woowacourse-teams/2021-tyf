import { useEffect, useState } from 'react';
import { MIN_DONATION_AMOUNT } from '../../constants/service';

const useDonationForm = () => {
  const [donationAmount, setDonationAmount] = useState('');

  const addDonationAmount = (amount: number) => {
    if (amount < 0) return;

    if (!donationAmount) setDonationAmount(amount.toString());

    setDonationAmount((Number(donationAmount) + amount).toString());
  };

  const isDonationAmountInValidRange = MIN_DONATION_AMOUNT <= Number(donationAmount);

  return {
    donationAmount: Number(donationAmount),
    setDonationAmount,
    addDonationAmount,
    isDonationAmountInValidRange,
  };
};

export default useDonationForm;
// export default useDonationForm;

// NOTE: 비제어 컴포넌트 버전

// const useDonationForm = () => {
//   const donationAmountInputRef = useRef<HTMLInputElement>(null);

//   const addDonationAmount = (amount: number) => {
//     if (!donationAmountInputRef.current || amount < 0) return;

//     if (!donationAmountInputRef.current.value) {
//       donationAmountInputRef.current.valueAsNumber = 0;
//     }

//     donationAmountInputRef.current.valueAsNumber += amount;
//   };

//   const isDonationAmountInValidRange = () => {
//     if (!donationAmountInputRef.current) return false;

//     return 1000 <= donationAmountInputRef.current.valueAsNumber;
//   };

//   return {
//     donationAmountInputRef,
//     addDonationAmount,
//     isDonationAmountInValidRange,
//   };
// };

// export default useDonationForm;
