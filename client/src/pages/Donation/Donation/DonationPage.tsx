import { useParams } from 'react-router-dom';

import { ParamTypes } from '../../../App';
import { FixedLogo } from '../../../components/@molecule/Logo/Logo';
import Profile from '../../../components/Creator/Profile/Profile';
import DonationForm from '../../../components/Donation/DonationForm/DonationForm';
import { popupWindow } from '../../../service/popup';
import { DonationPageTemplate } from './DonationPage.styles';

const DonationPage = () => {
  const { creatorId } = useParams<ParamTypes>();

  return (
    <DonationPageTemplate>
      <FixedLogo onClick={() => popupWindow('/')} />
      <section>
        <Profile />
      </section>
      <section>
        <DonationForm creatorId={creatorId} />
      </section>
    </DonationPageTemplate>
  );
};

export default DonationPage;
