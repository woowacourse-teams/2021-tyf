import { useParams } from 'react-router-dom';
import { ParamTypes } from '../../../App';

import MessageForm from '../../../components/Donation/MessageForm/DonationMessageForm';
import { StyledTemplate } from './DonationMessagePage.styles';

const DonationMessagePage = () => {
  const { creatorId } = useParams<ParamTypes>();

  return (
    <StyledTemplate>
      <MessageForm creatorId={creatorId} />
    </StyledTemplate>
  );
};

export default DonationMessagePage;
