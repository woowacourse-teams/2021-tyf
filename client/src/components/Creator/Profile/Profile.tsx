import { Link, useParams } from 'react-router-dom';

import defaultUserProfile from '../../../assets/images/default-user-profile.png';
import { NickName, ProfileImg, ProfileContainer } from './Profile.styles';
import { ParamTypes } from '../../../App';
import useCreator from '../../../service/hooks/useCreator';

const Profile = () => {
  const { creatorId } = useParams<ParamTypes>();
  const { nickname } = useCreator(creatorId);

  return (
    <ProfileContainer>
      <Link to={`/creator/${creatorId}`}>
        <ProfileImg src={defaultUserProfile} />
      </Link>
      <NickName>{nickname}</NickName>
    </ProfileContainer>
  );
};

export default Profile;
