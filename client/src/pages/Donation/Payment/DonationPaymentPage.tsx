import IconOutlineBarButton from '../../../components/@molecule/IconOutlineBarButton/IconOutlineBarButton';
import { FixedLogo } from '../../../components/@molecule/Logo/Logo';
import { popupWindow } from '../../../service/popup';
import {
  PaymentButtonContainer,
  DonationPaymentPageTemplate,
  StyledSubTitle,
} from './DonationPaymentPage.styles';
import KakaoPay from '../../../assets/icons/kakao-pay.svg';
import useDonation from '../../../service/hooks/useDonation';
import { useParams } from 'react-router-dom';
import { ParamTypes } from '../../../App';

const DonationPaymentPage = () => {
  const { creatorId } = useParams<ParamTypes>();
  const { donate } = useDonation(creatorId);

  return (
    <DonationPaymentPageTemplate>
      <FixedLogo onClick={() => popupWindow('/')} />
      <StyledSubTitle>결제수단을 선택해주세요!</StyledSubTitle>
      <PaymentButtonContainer>
        <IconOutlineBarButton src={KakaoPay} onClick={donate}>
          카카오페이
        </IconOutlineBarButton>
      </PaymentButtonContainer>
    </DonationPaymentPageTemplate>
  );
};

export default DonationPaymentPage;
