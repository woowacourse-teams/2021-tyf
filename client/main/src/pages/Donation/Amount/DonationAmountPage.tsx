import { useParams } from 'react-router-dom';

import { ParamTypes } from '../../../App';
import Anchor from '../../../components/@atom/Anchor/Anchor';
import Transition from '../../../components/@atom/Transition/Transition.styles';
import { FixedLogo } from '../../../components/@molecule/Logo/Logo';
import Profile from '../../../components/Creator/Profile/Profile';
import DonationAmountForm from '../../../components/Donation/AmountForm/DonationAmountForm';
import useAuthCheckEffect from '../../../service/@shared/useAuthCheckEffect';
import useUserInfo from '../../../service/user/useUserInfo';
import { toCommaSeparatedString } from '../../../utils/format';
import { popupWindow } from '../../../utils/popup';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';
import { DonationAmountPageTemplate, PointAnchor } from './DonationAmountPage.styles';

const DonationAmountPage = () => {
  const { creatorId } = useParams<ParamTypes>();
  const { userInfo } = useUserInfo();

  usePageRefreshGuardEffect(creatorId, true, '/donation/' + creatorId);
  useAuthCheckEffect(window.close);

  return (
    <DonationAmountPageTemplate>
      <FixedLogo onClick={() => popupWindow(window.location.origin)} />
      <section>
        <PointAnchor href={`${window.location.origin}/mypoint`} target="_blank" rel="noreferrer">
          {toCommaSeparatedString(userInfo?.point ?? 0)} tp
        </PointAnchor>
      </section>
      <section>
        <Transition>
          <Profile />
        </Transition>
      </section>
      <section>
        <Transition delay={0.2}>
          <DonationAmountForm creatorId={creatorId} />
        </Transition>
      </section>
    </DonationAmountPageTemplate>
  );
};

export default DonationAmountPage;
