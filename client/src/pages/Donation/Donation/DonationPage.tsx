import { FC, HTMLAttributes } from 'react';

import Profile from '../../../components/Creator/Profile/Profile';
import DonationForm from '../../../components/Donation/DonationForm/DonationForm';
import { DonationPageTemplate } from './DonationPage.styles';

const DonationPage: FC<HTMLAttributes<HTMLElement>> = () => {
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
