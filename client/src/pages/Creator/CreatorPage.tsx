import axios from 'axios';
import { FC, HTMLAttributes, Suspense } from 'react';
import { useHistory, useParams } from 'react-router';
import { ErrorBoundary } from 'react-error-boundary';

import { ParamTypes } from '../../App';
import useLoginUserInfo from '../../service/hooks/useLoginUserInfo';

import Profile from '../../components/Creator/Profile/Profile';
import Button from '../../components/@atom/Button/Button';
import DonationList from '../../components/Creator/DonationList/DonationList';
import { StyledTemplate, ProfileContainer, DescriptionContainer } from './CreatorPage.styles';
import { popupWindow } from '../../service/popup';
import { donationUrlShare } from '../../service/share';

const CreatorPage: FC<HTMLAttributes<HTMLElement>> = () => {
  const history = useHistory();
  const { creatorId } = useParams<ParamTypes>();
  const { userInfo } = useLoginUserInfo();
  const isAdmin = userInfo?.pageName === creatorId;

  const moveDonationPage = () => {
    popupWindow(`/donation/${creatorId}`, 'width=460,height=900,resizable=0');
  };

  const shareUrl = () => {
    if (!userInfo) return;

    donationUrlShare(userInfo.nickname, userInfo.pageName);
  };

  return (
    <StyledTemplate>
      <ErrorBoundary
        fallback={<p>잘못된 창작자 정보입니다</p>}
        onError={(error) => {
          if (axios.isAxiosError(error)) {
            alert(error.response?.data.message);
          } else {
            console.error(error);
            alert('잘못된 창작자 정보입니다!');
          }

          history.push('/');
        }}
      >
        <ProfileContainer>
          <Suspense fallback={<p>사용자 정보를 불러오는 중입니다.</p>}>
            <Profile />
            <DescriptionContainer>
              <p>제 페이지에 와주셔서 감사합니다!!</p>
            </DescriptionContainer>
            {isAdmin ? (
              <Button onClick={shareUrl}>내 페이지 공유하기</Button>
            ) : (
              <Button onClick={moveDonationPage}>후원하기</Button>
            )}
          </Suspense>
        </ProfileContainer>
        <Suspense fallback={true}>
          <DonationList isAdmin={isAdmin} />
        </Suspense>
      </ErrorBoundary>
    </StyledTemplate>
  );
};

export default CreatorPage;
