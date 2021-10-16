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

const SETTLEMENT_CONFIRM_MESSAGE = '아직 계좌인증이 되지 않았습니다. 계좌 인증을 진행하시겠습니까?';

interface CreatorInfoProps {
  isAdmin: boolean;
  creatorId: string;
  toggleModal: () => void;
}

const CreatorInfo = ({ isAdmin, creatorId, toggleModal }: CreatorInfoProps) => {
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
    if (!creator.bankRegistered && confirm(SETTLEMENT_CONFIRM_MESSAGE)) {
      history.push(`/creator/${creatorId}/settlement`);
      return;
    }

    donationUrlShare(creator.nickname, creatorId);
  };

  const openShareURLModal = () => {
    if (!creator.bankRegistered && confirm(SETTLEMENT_CONFIRM_MESSAGE)) {
      history.push(`/creator/${creatorId}/settlement`);
      return;
    }

    toggleModal();
  };

  return windowWidth > SIZE.DESKTOP_LARGE ? (
    <DesktopCreatorInfo
      creator={creator}
      isAdmin={isAdmin}
      shareUrl={openShareURLModal}
      bankRegistered={creator.bankRegistered}
      popupDonationAmountPage={popupDonationAmountPage}
    />
  ) : (
    <MobileCreatorInfo
      creator={creator}
      isAdmin={isAdmin}
      shareUrl={onMobileShare}
      bankRegistered={creator.bankRegistered}
      popupDonationAmountPage={popupDonationAmountPage}
    />
  );
};

export default CreatorInfo;
