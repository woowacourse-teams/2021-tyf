import { useState } from 'react';
import { useHistory } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { AUTH_ERROR_MESSAGE, PAYMENT_ERROR, PAYMENT_ERROR_MESSAGE } from '../../constants/error';
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
      switch (error.response.data.errorCode) {
        case PAYMENT_ERROR.EXCEED_TRY_COUNT:
          alert(PAYMENT_ERROR_MESSAGE[PAYMENT_ERROR.EXCEED_TRY_COUNT]);
          break;
        case PAYMENT_ERROR.PENDING:
          alert(PAYMENT_ERROR_MESSAGE[PAYMENT_ERROR.PENDING]);
          break;
        case PAYMENT_ERROR.NOT_READY:
          alert(PAYMENT_ERROR_MESSAGE[PAYMENT_ERROR.NOT_READY]);
          break;
        default:
          alert('유효하지 않은 주문번호입니다.');
      }
    }

    setIsVerificationEmailSending(false);
  };

  const refund = async () => {
    try {
      await requestRefund(refundInfo.refundAccessToken);
      alert('환불 신청이 완료 되었습니다.');
      history.push('/');
    } catch (error) {
      alert('환불 신청에 실패했습니다.');
    }
  };

  return { refundInfo, refund, verify, sendVerificationEmail, isVerificationEmailSending };
};

export default useRefund;
