import Input from '../../@atom/Input/Input.styles';
import SubTitle from '../../@atom/SubTitle/SubTitle.styles';
import TextButton from '../../@atom/TextButton/TextButton.styles';
import Title from '../../@atom/Title/Title.styles';
import { StyledRefundApplyForm } from './RefundApplyForm.styles';

const RefundApplyForm = () => {
  return (
    <StyledRefundApplyForm>
      <Title>환불신청</Title>
      <SubTitle>주문번호</SubTitle>
      <Input placeholder="주문번호 입력하기" />
      <TextButton>인증 메일 보내기</TextButton>
    </StyledRefundApplyForm>
  );
};

export default RefundApplyForm;
