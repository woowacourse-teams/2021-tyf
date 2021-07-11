import { FC, HTMLAttributes } from 'react';

import Comments from '../../components/Creator/Comments/Comments';
import Profile from '../../components/Creator/Profile/Profile';
import Button from '../../components/@atom/Button/Button';
import Template from '../../components/@atom/Template/Template';
import { CreatorPageContainer, ProfileContainer, DescriptionContainer } from './CreatorPage.styles';

const CreatorPage: FC<HTMLAttributes<HTMLElement>> = () => {
  return (
    <Template>
      <CreatorPageContainer>
        <ProfileContainer>
          <Profile />
          <DescriptionContainer>제 페이지에 와주셔서 감사합니다!!</DescriptionContainer>
          <Button>내 페이지 공유하기</Button>
        </ProfileContainer>
        <Comments />
      </CreatorPageContainer>
    </Template>
  );
};

export default CreatorPage;
