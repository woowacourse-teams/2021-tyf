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
        <SubTitle>주문번호</SubTitle>
        <Input
          placeholder="주문번호 입력하기"
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
        <p>결제일로부터 7일이내 요청에 대해서만 환불이 가능합니다.</p>
        <p>이후 환불건에 대해서는 도네이션을 받은 창작자와 협의가 필요합니다.</p>
      </CautionContainer>
    </StyledRefundApplyForm>
  );
};

export default RefundApplyForm;
