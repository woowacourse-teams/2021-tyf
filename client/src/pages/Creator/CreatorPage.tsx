import { FC, HTMLAttributes, Suspense, useEffect, useState } from 'react';

import Comments from '../../components/Creator/Comments/Comments';
import Profile from '../../components/Creator/Profile/Profile';
import Button from '../../components/@atom/Button/Button';
import { StyledTemplate, ProfileContainer, DescriptionContainer } from './CreatorPage.styles';

import { useHistory, useParams } from 'react-router';

import { ErrorBoundary } from 'react-error-boundary';
import axios from 'axios';

import useLoginUserInfo from '../../service/hooks/useLoginUserInfo';
import useCreator from '../../service/hooks/useCreator';
import { ParamTypes } from '../../App';

const CreatorPage: FC<HTMLAttributes<HTMLElement>> = () => {
  const history = useHistory();
  const { creatorId } = useParams<ParamTypes>();
  const { userInfo } = useLoginUserInfo();
  const { pageName } = useCreator(creatorId);
  const isAdmin = userInfo?.pageName === pageName;

  return (
    <StyledTemplate>
      {' '}
      <ErrorBoundary
        fallback={<p>잘못된 창작자 정보입니다</p>}
        onError={(error) => {
          if (axios.isAxiosError(error)) {
            alert(error.response?.data.message);
          } else {
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
            {isAdmin ? <Button>내 페이지 공유하기</Button> : <Button>후원하기</Button>}
          </Suspense>
        </ProfileContainer>
        <Suspense fallback={<p>후원기록을 불러오는 중입니다.</p>}>
          <Comments />
        </Suspense>
      </ErrorBoundary>
    </StyledTemplate>
  );
};

export default CreatorPage;
