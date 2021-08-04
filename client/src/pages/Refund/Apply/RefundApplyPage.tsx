import RefundApplyForm from '../../../components/Refund/ApplyForm/RefundApplyForm';
import { CautionContainer, RefundApplyPageTemplate } from './RefundApplyPage.styles';

const RefundApplyPage = () => {
  return (
    <RefundApplyPageTemplate>
      <RefundApplyForm />
      <CautionContainer>
        <p>결제일로부터 7일이내 요청에 대해서만 환불이 가능합니다.</p>
        <p>이후 환불건에 대해서는 도네이션을 받은 창작자와 협의가 필요합니다.</p>
      </CautionContainer>
    </RefundApplyPageTemplate>
  );
};

export default RefundApplyPage;
