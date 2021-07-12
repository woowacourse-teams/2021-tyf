import { FC, HTMLAttributes } from 'react';

import Anchor from '../../@atom/Anchor/Anchor';
import DefaultUserProfile from '../../../assets/images/defualtUserProfile.png';
import { NickName, ProfileImg, StyledProfileContainer } from './Profile.styles';

const Profile: FC<HTMLAttributes<HTMLElement>> = () => {
  return (
    <StyledProfileContainer>
      <Anchor to="">
        <ProfileImg style={{ backgroundImage: `url(${DefaultUserProfile})` }}></ProfileImg>
      </Anchor>
      <NickName>파노</NickName>
    </StyledProfileContainer>
  );
};

export default Profile;
