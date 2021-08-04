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
  return (
    <StyledRefundCertificationForm>
      <RefundCertificationTitle>
        <span>user1**@na***.com 으로 </span>
        <span>인증 메일이 </span>
        <span>전송되었습니다.</span>
      </RefundCertificationTitle>
      <CertificationInputContainer>
        <StyledSubTitle>인증번호</StyledSubTitle>
        <InputContainer>
          <CertInput placeholder="인증번호 입력하기" />
          <RestTime>2:59</RestTime>
        </InputContainer>
      </CertificationInputContainer>
      <ButtonContainer>
        <StyledButton>확인</StyledButton>
        <StyledTextButton>인증번호 다시 보내기 (1 / 5)</StyledTextButton>
      </ButtonContainer>
    </StyledRefundCertificationForm>
  );
};

export default RefundCertificationForm;
