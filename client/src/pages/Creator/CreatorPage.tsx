import axios from 'axios';
import { Suspense } from 'react';
import { useHistory, useParams } from 'react-router';
import { ErrorBoundary } from 'react-error-boundary';

import { ParamTypes } from '../../App';
import useLoginUserInfo from '../../service/hooks/useLoginUserInfo';

import Profile from '../../components/Creator/Profile/Profile';
import Button from '../../components/@atom/Button/Button';
import DonationMessageList from '../../components/Donation/MessageList/DonationMessageList';
import { StyledTemplate, DescriptionContainer } from './CreatorPage.styles';
import { popupWindow } from '../../service/popup';
import { donationUrlShare } from '../../service/share';
import { DONATION_POPUP } from '../../constants/popup';
import Spinner from '../../components/Spinner/Spinner';

const CreatorPage = () => {
  const history = useHistory();
  const { creatorId } = useParams<ParamTypes>();
  const { userInfo } = useLoginUserInfo();
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
          <section>
            <Profile />
            <DescriptionContainer>
              <p>제 페이지에 와주셔서 감사합니다!!</p>
            </DescriptionContainer>
            {isAdmin ? (
              <Button onClick={shareUrl}>내 페이지 공유하기</Button>
            ) : (
              <Button onClick={popupDonationPage}>후원하기</Button>
            )}
          </section>
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
