import { useHistory } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { IamportResponse } from '../../iamport';

import { CreatorId } from '../../types';

import { requestPayment, requestPaymentComplete } from '../request/payments';
import { donationState } from '../state/donation';
import useCreator from './useCreator';

const useDonation = (creatorId: CreatorId) => {
  const history = useHistory();
  const [donation, setDonation] = useRecoilState(donationState);
  const { pageName } = useCreator(creatorId);
  const { amount, email } = donation;

  const donate = async () => {
    const { merchantUid } = await requestPayment({ amount, email, pageName });
    const { IMP } = window;

    const accountId = 'imp52497817';

    IMP.init(accountId);

    const IMPRequestPayOption = {
      pg: 'kakaopay',
      pay_method: 'card',
      merchant_uid: merchantUid,
      name: pageName,
      amount,
      buyer_email: email,
      // buyer_name: '홍길동',
      // buyer_tel: '010-4242-4242',
      // buyer_addr: '서울특별시 강남구 신사동',
      // buyer_postcode: '01181',
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
