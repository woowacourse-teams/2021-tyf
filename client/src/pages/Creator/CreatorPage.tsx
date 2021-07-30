import { Suspense } from 'react';
import { useHistory, useParams } from 'react-router';
import { ErrorBoundary } from 'react-error-boundary';

import { ParamTypes } from '../../App';
import useUserInfo from '../../service/hooks/useUserInfo';
import DesktopCreatorInfo from '../../components/Creator/CreatorInfo/Desktop/DesktopCreatorInfo';
import MobileCreatorInfo from '../../components/Creator/CreatorInfo/Mobile/MobileCreatorInfo';
import DonationMessageList from '../../components/Donation/MessageList/DonationMessageList';
import useCreator from '../../service/hooks/useCreator';

import { useWindowResize } from '../../utils/useWindowResize';
import { StyledTemplate } from './CreatorPage.styles';
import { DONATION_POPUP } from '../../constants/popup';
import Spinner from '../../components/Spinner/Spinner';
import { SIZE } from '../../constants/device';
import defaultUserProfile from '../../assets/images/default-user-profile.png';
import { popupWindow } from '../../service/popup';
import URLShareModal from '../../components/ShareModal/URLShareModal/URLShareModal';
import useModal from '../../utils/useModal';
import { donationUrlShare } from '../../service/share';

const CreatorPage = () => {
  const history = useHistory();
  const { creatorId } = useParams<ParamTypes>();
  const { isOpen, toggleModal, closeModal } = useModal();
  const { userInfo } = useUserInfo();
  const { nickname, profileImage } = useCreator(creatorId);
  const { windowWidth } = useWindowResize();
  const isAdmin = userInfo?.pageName === creatorId;

  const popupDonationAmountPage = () => {
    popupWindow(window.location.origin + `/donation/${creatorId}`, {
      width: DONATION_POPUP.WIDTH,
      height: DONATION_POPUP.HEIGHT,
    });
  };

  const onMobileShare = () => {
    donationUrlShare(nickname, creatorId);
  };

  const openShareURLModal = () => {
    toggleModal();
  };

  const isModalOpen = windowWidth > SIZE.DESKTOP_LARGE && isOpen && userInfo;
  return (
    <StyledTemplate>
      <ErrorBoundary
        fallback={<p>AUTH_ERROR_MESSAGE[error]</p>}
        onError={() => {
          alert('잘못된 창작자 정보입니다!');

          history.push('/');
        }}
      >
        <Suspense fallback={<p>사용자 정보를 불러오는 중입니다.</p>}>
          {windowWidth > SIZE.DESKTOP_LARGE ? (
            <DesktopCreatorInfo
              defaultUserProfile={defaultUserProfile}
              profileImage={profileImage}
              nickname={nickname}
              isAdmin={isAdmin}
              shareUrl={openShareURLModal}
              popupDonationAmountPage={popupDonationAmountPage}
            />
          ) : (
            <MobileCreatorInfo
              isAdmin={isAdmin}
              shareUrl={onMobileShare}
              popupDonationAmountPage={popupDonationAmountPage}
            />
          )}
        </Suspense>

        <Suspense fallback={<Spinner />}>
          <section>
            <DonationMessageList isAdmin={isAdmin} />
          </section>
          {isModalOpen && <URLShareModal userInfo={userInfo!} onClose={closeModal} />}
        </Suspense>
      </ErrorBoundary>
    </StyledTemplate>
  );
};

export default CreatorPage;
