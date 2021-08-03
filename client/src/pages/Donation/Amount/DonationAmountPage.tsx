import { useParams } from 'react-router-dom';

import { ParamTypes } from '../../../App';
import { FixedLogo } from '../../../components/@molecule/Logo/Logo';
import Profile from '../../../components/Creator/Profile/Profile';
import DonationAmountForm from '../../../components/Donation/AmountForm/DonationAmountForm';
import usePageRefreshGuardEffect from '../../../service/hooks/usePageRefreshGuardEffect';
import { popupWindow } from '../../../service/popup';
import { DonationAmountPageTemplate } from './DonationAmountPage.styles';

const DonationAmountPage = () => {
  const { creatorId } = useParams<ParamTypes>();
  usePageRefreshGuardEffect(creatorId, true, '/donation/' + creatorId);

  return (
    <DonationAmountPageTemplate>
      <FixedLogo onClick={() => popupWindow(window.location.origin)} />
      <section>
        <Profile />
      </section>
      <section>
        <DonationAmountForm creatorId={creatorId} />
      </section>
    </DonationAmountPageTemplate>
  );
};

export default DonationAmountPage;
