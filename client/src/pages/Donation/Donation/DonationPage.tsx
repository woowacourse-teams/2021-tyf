import { VFC } from 'react';
import { useParams } from 'react-router-dom';
import { ParamTypes } from '../../../App';

import Profile from '../../../components/Creator/Profile/Profile';
import DonationForm from '../../../components/Donation/DonationForm/DonationForm';
import { DonationPageTemplate } from './DonationPage.styles';

const DonationPage: VFC = () => {
  const { creatorId } = useParams<ParamTypes>();

  return (
    <DonationPageTemplate>
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
