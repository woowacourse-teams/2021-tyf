import { useState } from 'react';
import { useHistory } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import {
  AUTH_ERROR_MESSAGE,
  PAYMENT_ERROR,
  PAYMENT_ERROR_MESSAGE,
  REFUND_ERROR_MESSAGE,
} from '../../constants/error';
import { requestRefund, requestVerify, requestVerifyMerchantUid } from '../@request/refund';
import { refundState } from '../@state/refund';

const useRefund = () => {
  const [refundInfo, setRefundInfo] = useRecoilState(refundState);
  const [isVerificationEmailSending, setIsVerificationEmailSending] = useState(false);
  const history = useHistory();

  const verify = async (merchantUid: string, verificationCode: string) => {
    try {
      const { refundAccessToken } = await requestVerify(merchantUid, verificationCode);

      setRefundInfo({ ...refundInfo, refundAccessToken });
      history.push('/refund/confirm');
    } catch (error) {
      if (error.response.data.errorCode === PAYMENT_ERROR.EXCEED_TRY_COUNT) {
        alert(PAYMENT_ERROR_MESSAGE[PAYMENT_ERROR.EXCEED_TRY_COUNT]);
        history.push('/refund');
        return;
      }

      alert(`잘못된 인증정보입니다. 남은 인증 횟수 (${error.response.data.remainTryCount} / 10)`);
    }
  };

  const sendVerificationEmail = async (merchantUid: string) => {
    setIsVerificationEmailSending(true);

    try {
      const result = await requestVerifyMerchantUid(merchantUid);

      setRefundInfo({ ...refundInfo, ...result, merchantUid });
      alert('이메일이 전송되었습니다.');
      history.push('/refund/cert');
    } catch (error) {
      const { errorCode }: { errorCode: keyof typeof PAYMENT_ERROR_MESSAGE } = error.response.data;
      const errorMessage = PAYMENT_ERROR_MESSAGE[errorCode] ?? '유효하지 않은 주문번호입니다.';

      alert(errorMessage);
    }

    setIsVerificationEmailSending(false);
  };

  const refund = async () => {
    try {
      await requestRefund(refundInfo.refundAccessToken);
      alert('환불 신청이 완료 되었습니다.');
      history.push('/');
    } catch (error) {
      const { errorCode }: { errorCode: keyof typeof REFUND_ERROR_MESSAGE } = error.response.data;
      const errorMessage =
        REFUND_ERROR_MESSAGE[errorCode] ??
        '환불 신청에 실패했습니다. 문제가 지속되면 고객센터로 문의해주세요.';
      alert(errorMessage);
    }
  };

  return { refundInfo, refund, verify, sendVerificationEmail, isVerificationEmailSending };
};

export default useRefund;
