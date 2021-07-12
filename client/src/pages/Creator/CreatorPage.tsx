import { FC, HTMLAttributes } from 'react';

import Comments from '../../components/Creator/Comments/Comments';
import Profile from '../../components/Creator/Profile/Profile';
import Button from '../../components/@atom/Button/Button';
import { StyledTemplate, ProfileContainer, DescriptionContainer } from './CreatorPage.styles';

const CreatorPage: FC<HTMLAttributes<HTMLElement>> = () => {
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
