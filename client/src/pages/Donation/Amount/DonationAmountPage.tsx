import { useParams } from 'react-router-dom';

import { ParamTypes } from '../../../App';
import Transition from '../../../components/@atom/Transition/Transition.styles';
import { FixedLogo } from '../../../components/@molecule/Logo/Logo';
import Profile from '../../../components/Creator/Profile/Profile';
import DonationAmountForm from '../../../components/Donation/AmountForm/DonationAmountForm';
import { popupWindow } from '../../../service/popup';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';
import { DonationAmountPageTemplate } from './DonationAmountPage.styles';

const DonationAmountPage = () => {
  const { creatorId } = useParams<ParamTypes>();

  usePageRefreshGuardEffect(creatorId, true, '/donation/' + creatorId);

  return (
    <DonationAmountPageTemplate>
      <FixedLogo onClick={() => popupWindow(window.location.origin)} />
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
