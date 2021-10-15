import { SIZE } from '../../../constants/device';
import { DONATION_POPUP } from '../../../constants/popup';
import useCreator from '../../../service//creator/useCreator';
import { popupWindow } from '../../../utils/popup';
import { donationUrlShare } from '../../../service/creator/donationURLShare';
import { useWindowResize } from '../../../utils/useWindowResize';
import DesktopCreatorInfo from './Desktop/DesktopCreatorInfo';
import MobileCreatorInfo from './Mobile/MobileCreatorInfo';
import useAccessToken from '../../../service/@shared/useAccessToken';
import { useHistory } from 'react-router';

interface CreatorInfoProps {
  isAdmin: boolean;
  creatorId: string;
  toggleModal: () => void;
  bankRegistered: boolean;
}

const CreatorInfo = ({ isAdmin, creatorId, bankRegistered, toggleModal }: CreatorInfoProps) => {
  const history = useHistory();
  const creator = useCreator(creatorId);
  const { windowWidth } = useWindowResize();
  const { accessToken } = useAccessToken();

  const popupDonationAmountPage = () => {
    if (!accessToken) {
      alert('도네이션을 하기 위해서 로그인을 해주세요.');
      history.push(`/login?redirectTo=${window.location.pathname}`);
      return;
    }

    popupWindow(window.location.origin + `/donation/${creatorId}`, {
      width: DONATION_POPUP.WIDTH,
      height: DONATION_POPUP.HEIGHT,
    });
  };

  const onMobileShare = () => {
    donationUrlShare(creator.nickname, creatorId);
  };

  const openShareURLModal = () => {
    toggleModal();
  };

  return windowWidth > SIZE.DESKTOP_LARGE ? (
    <DesktopCreatorInfo
      creator={creator}
      isAdmin={isAdmin}
      shareUrl={openShareURLModal}
      bankRegistered={bankRegistered}
      popupDonationAmountPage={popupDonationAmountPage}
    />
  ) : (
    <MobileCreatorInfo
      creator={creator}
      isAdmin={isAdmin}
      shareUrl={onMobileShare}
      bankRegistered={bankRegistered}
      popupDonationAmountPage={popupDonationAmountPage}
    />
  );
};

export default CreatorInfo;
