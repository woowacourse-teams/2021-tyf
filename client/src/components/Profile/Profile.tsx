import { FC, HTMLAttributes } from 'react';

import Title from '../@atom/SubTitle/SubTitle';
import { ProfileImg, StyledProfileContainer } from './Profile.styles';

import DefaultUserProfile from '../../assets/images/defualtUserProfile.png';
import Anchor from '../@atom/Anchor/Anchor';

const Profile: FC<HTMLAttributes<HTMLElement>> = () => {
  return (
    <StyledProfileContainer>
      <Anchor href="">
        <ProfileImg style={{ backgroundImage: `url(${DefaultUserProfile})` }}></ProfileImg>
      </Anchor>
      <Title>파노</Title>
    </StyledProfileContainer>
  );
};

export default Profile;
