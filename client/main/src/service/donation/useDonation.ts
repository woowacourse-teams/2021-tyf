import { useHistory } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { IamportResponse, RequestPayParams } from '../../iamport';

import { CreatorId } from '../../types';
import { requestPayment, requestPaymentComplete } from '../@request/payments';
import { donationState } from '../@state/donation';
import useCreator from '../creator/useCreator';

const useDonation = (creatorId: CreatorId) => {
  const history = useHistory();
  const [donation, setDonation] = useRecoilState(donationState);
  const { pageName } = useCreator(creatorId);
  const { amount, email } = donation;

  const donate = async () => {
    const { merchantUid } = await requestPayment({ amount, email, pageName });
    const { IMP } = window;

    const prodAccountId = 'imp52497817';
    const devAccountId = 'imp61348931';

    const accountId = process.env.NODE_ENV === 'development' ? devAccountId : prodAccountId;

    IMP.init(accountId);

    const IMPRequestPayOption: RequestPayParams = {
      pg: '카카오페이',
      pay_method: 'card',
      merchant_uid: merchantUid,
      name: pageName,
      amount,
      buyer_email: email,
    };

    const IMPResponseHandler = async (response: IamportResponse) => {
      if (!response.success) {
        alert(`결제에 실패했습니다. 다시 시도해주세요.\n 에러 내역: ${response.error_msg}`);
        window.close();
        return;
      }

      try {
        const donationResult = await requestPaymentComplete(response);

        setDonation(donationResult);
        alert('결제에 성공했습니다.');
        history.push(`/donation/${pageName}/message`);
      } catch (error) {
        alert(`결제에 실패했습니다. 다시 시도해주세요. ${error.message}`);
        window.close();
      }
    };

    IMP.request_pay(IMPRequestPayOption, IMPResponseHandler);
  };

  return { donate, donation };
};

export default useDonation;
