import { useHistory } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { requestRefund, requestVerify, requestVerifyMerchantUid } from '../@request/refund';
import { refundState } from '../@state/refund';

const useRefund = () => {
  const [refundInfo, setRefundInfo] = useRecoilState(refundState);
  const history = useHistory();

  const verify = async (merchantUid: string, verificationCode: string) => {
    try {
      const { refundAccessToken } = await requestVerify(merchantUid, verificationCode);

      setRefundInfo({ ...refundInfo, refundAccessToken });
      history.push('/refund/confirm');
    } catch (error) {
      alert(`잘못된 인증정보입니다. 남은 인증 횟수 (${error.response.data.remainTryCount}/ 10)`);
    }
  };

  const sendVerificationEmail = async (merchantUid: string) => {
    try {
      const result = await requestVerifyMerchantUid(merchantUid);

      setRefundInfo({ ...refundInfo, ...result, merchantUid });
      alert('이메일이 전송되었습니다.');
      history.push('/refund/cert');
    } catch (error) {
      alert('유효하지 않은 주문번호입니다.');
    }
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

  return { refundInfo, refund, verify, sendVerificationEmail };
};

export default useRefund;
