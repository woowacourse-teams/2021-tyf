import Button from '../../../components/@atom/Button/Button.styles';
import TextButton from '../../../components/@atom/TextButton/TextButton.styles';
import RefundInfo from '../../../components/Refund/RefundInfo/RefundInfo';
import { RefundPageTemplate, ButtonContainer } from './RefundPage.styles';

const RefundPage = () => {
  return (
    <RefundPageTemplate>
      <RefundInfo />
      <ButtonContainer>
        <Button>환불 신청하기</Button>
        <TextButton>나가기</TextButton>
      </ButtonContainer>
    </RefundPageTemplate>
  );
};

export default RefundPage;
