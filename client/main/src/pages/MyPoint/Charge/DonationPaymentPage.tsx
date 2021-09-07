import IconOutlineBarButton from '../../../components/@molecule/IconOutlineBarButton/IconOutlineBarButton';
import { FixedLogo } from '../../../components/@molecule/Logo/Logo';
import { popupWindow } from '../../../utils/popup';
import {
  PaymentButtonContainer,
  DonationPaymentPageTemplate,
  StyledSubTitle,
} from './DonationPaymentPage.styles';
import KakaoPay from '../../../assets/icons/kakao-pay.svg';
import useDonation from '../../../service/donation/useDonation';
import { useParams } from 'react-router-dom';
import { ParamTypes } from '../../../App';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';
import Transition from '../../../components/@atom/Transition/Transition.styles';

const DonationPaymentPage = () => {
  const { creatorId } = useParams<ParamTypes>();
  const { donate } = useDonation();

  usePageRefreshGuardEffect(creatorId, false, '/donation/' + creatorId);

  return (
    <DonationPaymentPageTemplate>
      <FixedLogo onClick={() => popupWindow(window.location.origin)} />
    </DonationPaymentPageTemplate>
  );
};

export default DonationPaymentPage;
