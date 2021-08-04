import { useParams } from 'react-router-dom';

import { ParamTypes } from '../../../App';
import { FixedLogo } from '../../../components/@molecule/Logo/Logo';
import DonatorForm from '../../../components/Donation/DonatorForm/DonatorForm';
import { popupWindow } from '../../../service/popup';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';
import { DonatorInfoPageTemplate } from './DonatorInfoPage.styles';

const DonatorInfoPage = () => {
  const { creatorId } = useParams<ParamTypes>();
  usePageRefreshGuardEffect(creatorId, false, '/donation/' + creatorId);

  return (
    <DonatorInfoPageTemplate>
      <FixedLogo onClick={() => popupWindow(window.location.origin)} />
      <DonatorForm creatorId={creatorId} />
    </DonatorInfoPageTemplate>
  );
};

export default DonatorInfoPage;
