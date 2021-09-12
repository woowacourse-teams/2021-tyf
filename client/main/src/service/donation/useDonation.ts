import { useHistory } from 'react-router-dom';
import { useRecoilState } from 'recoil';

import { CreatorId } from '../../types';
import { requestDonation } from '../@request/donation';
import useAccessToken from '../@shared/useAccessToken';
import { donationState } from '../@state/donation';

const useDonation = () => {
  const history = useHistory();
  const { accessToken } = useAccessToken();
  const [donation, setDonation] = useRecoilState(donationState);

  const donate = async (creatorId: CreatorId, donationAmount: number) => {
    try {
      const result = await requestDonation(creatorId, donationAmount, accessToken);
      const { donationId, message, donatedPoint } = result;

      setDonation({ ...donation, donationId, donatedPoint, message });

      history.push(`/donation/${creatorId}/message`);
    } catch (error) {
      alert('도네이션에 실패했습니다.');
    }
  };

  return { donate, donation };
};

export default useDonation;
