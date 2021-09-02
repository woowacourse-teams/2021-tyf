import IconOutlineBarButton from '../../../components/@molecule/IconOutlineBarButton/IconOutlineBarButton';
import { FixedLogo } from '../../../components/@molecule/Logo/Logo';
import { popupWindow } from '../../../utils/popup';
import {
  PaymentButtonContainer,
  DonationPaymentPageTemplate,
  StyledSubTitle,
} from './DonationPaymentPage.styles';
import KakaoPay from '../../../assets/icons/kakao-pay.svg';

import useDonation from '../../../service//donation/useDonation';
import { useParams } from 'react-router-dom';
import { ParamTypes } from '../../../App';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';
import Transition from '../../../components/@atom/Transition/Transition.styles';
import OutlineButton from '../../../components/@molecule/OutlineButton/OutlineButton.styles';

const DonationPaymentPage = () => {
  const { creatorId } = useParams<ParamTypes>();
  const { donate } = useDonation(creatorId);

  usePageRefreshGuardEffect(creatorId, false, '/donation/' + creatorId);

  return (
    <DonationPaymentPageTemplate>
      <FixedLogo onClick={() => popupWindow(window.location.origin)} />
      <Transition>
        <StyledSubTitle>결제수단을 선택해주세요!</StyledSubTitle>
        <PaymentButtonContainer>
          <OutlineButton onClick={() => donate('uplus')}>일반 결제</OutlineButton>
          <IconOutlineBarButton src={KakaoPay} onClick={() => donate('kakaopay')}>
            카카오페이
          </IconOutlineBarButton>
        </PaymentButtonContainer>
      </Transition>
    </DonationPaymentPageTemplate>
  );
};

export default DonationPaymentPage;
