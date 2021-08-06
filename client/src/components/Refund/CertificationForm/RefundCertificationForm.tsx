import { FormEvent, useState } from 'react';

import useRefund from '../../../service/hooks/refund/useRefund';
import { secToMin } from '../../../utils/format';
import useTimer from '../../../utils/useTimer';
import RegisterAddressForm from '../../Register/AddressForm/RegisterAddressForm';
import {
  CertificationInputContainer,
  CertInput,
  InputContainer,
  RefundCertificationTitle,
  RestTime,
  StyledButton,
  StyledRefundCertificationForm,
  ButtonContainer,
  StyledSubTitle,
  StyledTextButton,
} from './RefundCertificationForm.styles';

const RefundCertificationForm = () => {
  const [verificationCode, setVerificationCode] = useState('');
  const { refundInfo, verify, sendVerificationEmail } = useRefund();
  const { remainTime: timeout, resetTimer: resetTimeout } = useTimer(refundInfo.timeout);
  const { remainTime: resendCoolTime, resetTimer: resetCoolTime } = useTimer(
    refundInfo.resendCoolTime
  );

  const onSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    verify(refundInfo.merchantUid, verificationCode);
  };

  const onResend = () => {
    if (resendCoolTime > 0) {
      alert(`이메일 재전송 가능시간까지 ${resendCoolTime}초 남았습니다.`);
      return;
    }

    sendVerificationEmail(refundInfo.merchantUid);
    resetTimeout();
    resetCoolTime();
  };

  const isDisabled = timeout <= 0 || !verificationCode.length;

  return (
    <StyledRefundCertificationForm onSubmit={onSubmit}>
      <RefundCertificationTitle>
        <span>{refundInfo.email} 으로 </span>
        <span>인증 메일이 </span>
        <span>전송되었습니다.</span>
      </RefundCertificationTitle>
      <CertificationInputContainer>
        <StyledSubTitle>인증번호</StyledSubTitle>
        <InputContainer>
          <CertInput
            placeholder="인증번호 입력하기"
            value={verificationCode}
            onChange={({ target }) => setVerificationCode(target.value)}
          />
          <RestTime>{secToMin(timeout)}</RestTime>
        </InputContainer>
      </CertificationInputContainer>
      <ButtonContainer>
        <StyledButton disabled={isDisabled}>확인</StyledButton>
        <StyledTextButton type="button" onClick={onResend}>
          인증번호 다시 보내기
        </StyledTextButton>
      </ButtonContainer>
    </StyledRefundCertificationForm>
  );
};

export default RefundCertificationForm;
