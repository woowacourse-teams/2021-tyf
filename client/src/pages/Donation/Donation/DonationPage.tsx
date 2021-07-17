import { VFC } from 'react';

import Profile from '../../../components/Creator/Profile/Profile';
import DonationForm from '../../../components/Donation/DonationForm/DonationForm';
import { DonationPageTemplate } from './DonationPage.styles';

const DonationPage: VFC = () => {
  return (
    <DonationPageTemplate>
      <section>
        <Profile />
      </section>
      <section>
        <DonationForm />
      </section>
    </DonationPageTemplate>
  );
};

export default DonationPage;
