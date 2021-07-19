import { useParams } from 'react-router-dom';
import { ParamTypes } from '../../../App';
import { FixedLogo } from '../../../components/@molecule/Logo/Logo';

import MessageForm from '../../../components/Donation/MessageForm/DonationMessageForm';
import { popupWindow } from '../../../service/popup';
import { StyledTemplate } from './DonationMessagePage.styles';

const DonationMessagePage = () => {
  const { creatorId } = useParams<ParamTypes>();

  return (
    <StyledTemplate>
      <FixedLogo onClick={() => popupWindow('/')} />
      <MessageForm creatorId={creatorId} />
    </StyledTemplate>
  );
};

export default DonationMessagePage;
