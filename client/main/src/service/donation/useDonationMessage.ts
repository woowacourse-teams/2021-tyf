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

    try {
      await requestSendDonationMessage(donation.donationId, finalMessage, isSecret);

      history.push(`/donation/${creatorId}/success`);
    } catch (error) {
      alert('도네이션 메세지 전송에 실패했습니다. 잠시후 다시 시도해주세요.');
    }
  };

  return { sendDonationMessage };
};

export default useDonationMessage;
