import { Link, useParams } from 'react-router-dom';

import defaultUserProfile from '../../../assets/images/default-user-profile.png';
import { NickName, ProfileImg } from './Profile.styles';
import { ParamTypes } from '../../../App';
import useCreator from '../../../service/hooks/useCreator';

const Profile = () => {
  const { creatorId } = useParams<ParamTypes>();
  const { nickname, profileImage } = useCreator(creatorId);

  return (
    <>
      <Link to={`/creator/${creatorId}`}>
        <ProfileImg src={profileImage || defaultUserProfile} />
      </Link>
      <NickName>{nickname}</NickName>
    </>
  );
};

export default Profile;
