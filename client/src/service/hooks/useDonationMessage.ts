import { useHistory } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { CreatorId } from '../../types';
import { requestSendDonationMessage } from '../request/donation';
import { donationState } from '../state/donation';

const useDonationMessage = (creatorId: CreatorId) => {
  const donation = useRecoilValue(donationState);
  const history = useHistory();

  const sendDonationMessage = async (name: string, message: string, secret: boolean) => {
    const finalName = name || donation.name;
    const finalMessage = message || donation.message;

    try {
      await requestSendDonationMessage(donation.donationId, finalName, finalMessage, secret);

      history.push(`/donation/${creatorId}/success`);
    } catch (error) {
      alert('메세지 전송에 실패했습니다.');
    }
  };

  return { sendDonationMessage };
};

export default useDonationMessage;
