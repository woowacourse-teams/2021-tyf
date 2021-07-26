import { Suspense } from 'react';
import { useHistory, useParams } from 'react-router';
import { ErrorBoundary } from 'react-error-boundary';

import { ParamTypes } from '../../App';
import useUserInfo from '../../service/hooks/useUserInfo';
import Profile from '../../components/Creator/Profile/Profile';
import Spinner from '../../components/Spinner/Spinner';
import DesktopCreatorInfo from '../../components/Creator/CreatorInfo/Desktop/DesktopCreatorInfo';
import MobileCreatorInfo from '../../components/Creator/CreatorInfo/Mobile/MobileCreatorInfo';
import DonationMessageList from '../../components/Donation/MessageList/DonationMessageList';
import useCreator from '../../service/hooks/useCreator';
import useLoginUserInfo from '../../service/hooks/useLoginUserInfo';
import { popupWindow } from '../../service/popup';
import { donationUrlShare } from '../../service/share';
import { useWindowResize } from '../../utils/useWindowResize';
import { StyledTemplate } from './CreatorPage.styles';
import { DONATION_POPUP } from '../../constants/popup';
import Spinner from '../../components/Setting/Spinner/Spinner';
import { SIZE } from '../../constants/device';
import defaultUserProfile from '../../assets/images/default-user-profile.png';

const CreatorPage = () => {
  const history = useHistory();
  const { creatorId } = useParams<ParamTypes>();
  const { userInfo } = useUserInfo();
  const { nickname } = useCreator(creatorId);
  const { windowWidth } = useWindowResize();
  const isAdmin = userInfo?.pageName === creatorId;

  const popupDonationPage = () => {
    popupWindow(
      `/donation/${creatorId}`,
      `width=${DONATION_POPUP.WIDTH},height=${DONATION_POPUP.HEIGHT}`
    );
  };

  const shareUrl = () => {
    if (!userInfo) return;

    donationUrlShare(userInfo.nickname, userInfo.pageName);
  };

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
              nickname={nickname}
              isAdmin={isAdmin}
              shareUrl={shareUrl}
              popupDonationPage={popupDonationPage}
            />
          ) : (
            <MobileCreatorInfo
              isAdmin={isAdmin}
              shareUrl={shareUrl}
              popupDonationPage={popupDonationPage}
            />
          )}
        </Suspense>

        <Suspense fallback={<Spinner />}>
          <section>
            <DonationMessageList isAdmin={isAdmin} />
          </section>
        </Suspense>
      </ErrorBoundary>
    </StyledTemplate>
  );
};

export default CreatorPage;
