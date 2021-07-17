import { FC, HTMLAttributes } from 'react';

import Comments from '../../components/Creator/Comments/Comments';
import Profile from '../../components/Creator/Profile/Profile';
import Button from '../../components/@atom/Button/Button';
import { StyledTemplate, ProfileContainer, DescriptionContainer } from './CreatorPage.styles';

const CreatorPage: FC<HTMLAttributes<HTMLElement>> = () => {
  // 1. 해당 창작자 정보 있는지 확인하기 (없으면 홈으로 리다이렉트)
  // 2. 현재 로그인 한 사람과 창작자가 동일한 사람인지 확인하기
  // 3. 해당 창작자 정보 불러오기
  // 4. 해당 창작자 후원 목록 불러오기 (2번에 따라 다른 결과)

  return (
    <StyledTemplate>
      <ProfileContainer>
        <Profile />
        <DescriptionContainer>
          <p>제 페이지에 와주셔서 감사합니다!!</p>
        </DescriptionContainer>
        <Button>내 페이지 공유하기</Button>
      </ProfileContainer>
      <Comments />
    </StyledTemplate>
  );
};

export default CreatorPage;
