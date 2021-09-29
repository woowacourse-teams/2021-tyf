import { useHistory } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { CreatorId } from '../../types';
import { requestSendDonationMessage } from '../@request/donation';
import { donationState } from '../@state/donation';

const useDonationMessage = (creatorId: CreatorId) => {
  const donation = useRecoilValue(donationState);
  const history = useHistory();

  const sendDonationMessage = async (message: string, isSecret: boolean) => {
    const finalMessage = message || donation.message;

    console.dir(donation);

    try {
      await requestSendDonationMessage(donation.donationId, donation.name, finalMessage, isSecret);

      history.push(`/donation/${creatorId}/success`);
    } catch (error) {
      alert('메세지 전송에 실패했습니다.');
    }
  };

  return { sendDonationMessage };
};

export default useDonationMessage;
