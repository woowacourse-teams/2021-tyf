import { useHistory } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { CreatorId } from '../../types';
import { requestDonation } from '../request/donation';
import { donationState } from '../state/donation';

const useDonation = (creatorId: CreatorId) => {
  const history = useHistory();
  const setDonation = useSetRecoilState(donationState);

  const donate = async (amount: number) => {
    try {
      const donationResult = await requestDonation(creatorId, amount);

      setDonation(donationResult);

      history.push(`/donation/${creatorId}/message`);
    } catch (error) {
      alert('도네이션에 실패했습니다.');
    }
  };

  return { donate };
};

export default useDonation;
