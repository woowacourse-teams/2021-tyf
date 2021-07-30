import IconOutlineBarButton from '../../../components/@molecule/IconOutlineBarButton/IconOutlineBarButton';
import { FixedLogo } from '../../../components/@molecule/Logo/Logo';
import { popupWindow } from '../../../service/popup';
import {
  PaymentButtonContainer,
  DonationPaymentPageTemplate,
  StyledSubTitle,
} from './DonationPaymentPage.styles';
import KakaoPay from '../../../assets/icons/kakao-pay.svg';

const DonationPaymentPage = () => {
  // const { donate } = useDonation(creatorId);
  // donate(Number(donationAmount));

  return (
    <DonationPaymentPageTemplate>
      <FixedLogo onClick={() => popupWindow('/')} />
      <StyledSubTitle>결제수단을 선택해주세요!</StyledSubTitle>
      <PaymentButtonContainer>
        <IconOutlineBarButton src={KakaoPay}>카카오페이</IconOutlineBarButton>
      </PaymentButtonContainer>
    </DonationPaymentPageTemplate>
  );
};

export default DonationPaymentPage;
