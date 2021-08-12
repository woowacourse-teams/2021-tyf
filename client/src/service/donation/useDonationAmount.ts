import { useSetRecoilState } from 'recoil';
import { donationState } from '../@state/donation';

const useDonationAmount = () => {
  const setDonation = useSetRecoilState(donationState);

  const setDonationAmount = (amount: number) => {
    setDonation((prev) => ({ ...prev, amount }));
  };

  return { setDonationAmount };
};

export default useDonationAmount;
