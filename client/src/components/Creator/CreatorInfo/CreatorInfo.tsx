import { SIZE } from '../../../constants/device';
import { DONATION_POPUP } from '../../../constants/popup';
import useCreator from '../../../service/hooks/creator/useCreator';
import { popupWindow } from '../../../service/popup';
import { donationUrlShare } from '../../../service/share';
import { useWindowResize } from '../../../utils/useWindowResize';
import DesktopCreatorInfo from './Desktop/DesktopCreatorInfo';
import MobileCreatorInfo from './Mobile/MobileCreatorInfo';

interface CreatorInfoProps {
  isAdmin: boolean;
  creatorId: string;
  toggleModal: () => void;
}

const CreatorInfo = ({ isAdmin, creatorId, toggleModal }: CreatorInfoProps) => {
  const creator = useCreator(creatorId);
  const { windowWidth } = useWindowResize();

  const popupDonationAmountPage = () => {
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
      popupDonationAmountPage={popupDonationAmountPage}
    />
  ) : (
    <MobileCreatorInfo
      creator={creator}
      isAdmin={isAdmin}
      shareUrl={onMobileShare}
      popupDonationAmountPage={popupDonationAmountPage}
    />
  );
};

export default CreatorInfo;
