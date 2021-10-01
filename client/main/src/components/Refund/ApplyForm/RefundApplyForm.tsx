import { FormEvent, useState } from 'react';

import useRefund from '../../../service/refund/useRefund';
import Container from '../../@atom/Container/Container.styles';
import Input from '../../@atom/Input/Input.styles';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import TextButton from '../../@atom/TextButton/TextButton.styles';
import Title from '../../@atom/Title/Title.styles';
import { CautionContainer, StyledRefundApplyForm } from './RefundApplyForm.styles';

const RefundApplyForm = () => {
  const [merchantUid, setMerchantUid] = useState('');
  const { sendVerificationEmail, isVerificationEmailSending } = useRefund();

  const onSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    sendVerificationEmail(merchantUid);
  };

  return (
    <StyledRefundApplyForm onSubmit={onSubmit}>
      <Title>환불신청</Title>
      <Container>
        <SubTitle>결제번호</SubTitle>
        <Input
          placeholder="결제번호 입력하기"
          value={merchantUid}
          onChange={({ target }) => setMerchantUid(target.value)}
        />
      </Container>
      {isVerificationEmailSending ? (
        <TextButton disabled>보내는 중...</TextButton>
      ) : (
        <TextButton>인증 메일 보내기</TextButton>
      )}
      <CautionContainer>
        <p>미사용 포인트는 결제일로부터 7일이내 요청에 대해서 카드 취소 환불이 가능합니다.</p>
        <p>이외의 환불 문의에 대해서는 고객센터로 문의해주세요.</p>
        <p>사용한 포인트에 대해서는 환불이 불가능합니다.</p>
      </CautionContainer>
    </StyledRefundApplyForm>
  );
};

export default RefundApplyForm;
