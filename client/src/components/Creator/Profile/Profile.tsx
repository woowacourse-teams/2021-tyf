import { VFC } from 'react';

import defaultUserProfile from '../../../assets/images/defaultUserProfile.png';
import { NickName, ProfileImg, ProfileContainer } from './Profile.styles';
import { useParams } from 'react-router-dom';
import { ParamTypes } from '../../../App';
import useCreator from '../../../service/hooks/useCreator';

const Profile: VFC = () => {
  const { creatorId } = useParams<ParamTypes>();
  const { nickname } = useCreator(creatorId);

  return (
    <ProfileContainer>
      <a href={window.location.origin + `/creator/${creatorId}`}>
        <ProfileImg style={{ backgroundImage: `url(${defaultUserProfile})` }}></ProfileImg>
      </a>
      <NickName>{nickname}</NickName>
    </ProfileContainer>
  );
};

export default Profile;
