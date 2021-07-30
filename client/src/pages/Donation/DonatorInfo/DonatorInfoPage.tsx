import { useParams } from 'react-router-dom';

import { ParamTypes } from '../../../App';
import { FixedLogo } from '../../../components/@molecule/Logo/Logo';
import DonatorForm from '../../../components/Donation/DonatorForm/DonatorForm';
import { popupWindow } from '../../../service/popup';
import { DonatorInfoPageTemplate } from './DonatorInfoPage.styles';

const DonatorInfoPage = () => {
  const { creatorId } = useParams<ParamTypes>();

  return (
    <DonatorInfoPageTemplate>
      <FixedLogo onClick={() => popupWindow('/')} />
      <DonatorForm creatorId={creatorId} />
    </DonatorInfoPageTemplate>
  );
};

export default DonatorInfoPage;
