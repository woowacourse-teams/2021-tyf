import { VFC } from 'react';

import MessageForm from '../../../components/Donation/MessageForm/DonationMessageForm';
import { StyledTemplate } from './DonationMessagePage.styles';

const DonationMessagePage: VFC = () => {
  return (
    <StyledTemplate>
      <MessageForm />
    </StyledTemplate>
  );
};

export default DonationMessagePage;
