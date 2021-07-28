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
  const { pageName, nickname } = useCreator(creatorId);

  const donate = async (amount: number) => {
    const { merchantUid } = await requestPayment({ amount, email: '', pageName });
    const { IMP } = window;

    console.log(merchantUid, pageName, nickname, amount);

    IMP.init('imp61348931'); // TODO: 가맹점 식별번호 입력

    // TODO : 후원자 정보 입력 form 페이지 추가하기
    IMP.request_pay(
      {
        pg: 'kakaopay',
        pay_method: 'card',
        merchant_uid: merchantUid,
        name: pageName,
        amount,
        buyer_email: 'gildong@gmail.com',
        buyer_name: '홍길동',
        buyer_tel: '010-4242-4242',
        buyer_addr: '서울특별시 강남구 신사동',
        buyer_postcode: '01181',
      },
      async (response: IamportResponse) => {
        console.log(response);
        if (response.success) {
          try {
            const donationResult = await requestPaymentComplete(response);

            setDonation(donationResult);

            alert('결제에 성공했습니다.');
            history.push(`/donation/${pageName}/message`);
          } catch (error) {
            alert(`결제에 실패했습니다. 다시 시도해주세요.${error.message}`);
            window.close();
          }
        } else {
          alert(`결제에 실패했습니다. 다시 시도해주세요.\n 에러 내역: ${response.error_msg}`);
          window.close();
        }
      }
    );
  };

  return { donate, donation };
};

export default useDonation;
