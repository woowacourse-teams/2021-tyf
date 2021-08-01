import { useHistory } from 'react-router-dom';
import { useRecoilState } from 'recoil';

import { CreatorId } from '../../types';
import { requestDonation } from '../request/donation';
import { donationState } from '../state/donation';

const useDonation = (creatorId: CreatorId) => {
  const history = useHistory();
  const [donation, setDonation] = useRecoilState(donationState);

  const donate = async (amount: number) => {
    try {
      const donationResult = await requestDonation(creatorId, amount);

      setDonation(donationResult);

      history.push(`/donation/${creatorId}/message`);
    } catch (error) {
      alert('후원에 실패했습니다.');
    }
  };

  return { donate, donation };
};

export default useDonation;
