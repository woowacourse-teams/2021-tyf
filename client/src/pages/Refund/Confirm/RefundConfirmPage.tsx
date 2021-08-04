import Button from '../../../components/@atom/Button/Button.styles';
import TextButton from '../../../components/@atom/TextButton/TextButton.styles';
import RefundInfo from '../../../components/Refund/RefundInfo/RefundInfo';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';
import { REFUND_PAGE_KEY } from '../Apply/RefundApplyPage';
import { RefundConfirmPageTemplate, ButtonContainer } from './RefundConfirmPage.styles';

const RefundConfirmPage = () => {
  usePageRefreshGuardEffect(REFUND_PAGE_KEY, false, '/refund');

  return (
    <RefundConfirmPageTemplate>
      <RefundInfo />
      <ButtonContainer>
        <Button>환불 신청하기</Button>
        <TextButton>나가기</TextButton>
      </ButtonContainer>
    </RefundConfirmPageTemplate>
  );
};

export default RefundConfirmPage;
