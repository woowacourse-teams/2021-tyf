import { useHistory } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { DONATION_ERROR, DONATION_ERROR_MESSAGE } from '../../constants/error';

import { CreatorId } from '../../types';
import { popupWindow } from '../../utils/popup';
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
      const { donationId, message, amount, name } = result;

      setDonation({ ...donation, donationId, amount, name, message });

      history.push(`/donation/${creatorId}/message`);
    } catch (error) {
      const { errorCode }: { errorCode: keyof typeof DONATION_ERROR_MESSAGE } = error.response.data;
      const errorMessage = DONATION_ERROR_MESSAGE[errorCode] ?? '도네이션에 실패했습니다.';

      if (errorCode === DONATION_ERROR.NOT_ENOUGH_POINT) {
        if (!window.confirm(errorMessage)) return;

        popupWindow(`${window.location.origin}/mypoint?isChargeModalOpen=true`, {
          width: 600,
          height: 800,
        });

        return;
      }

      alert(errorMessage);
    }
  };

  return { donate, donation };
};

export default useDonation;
