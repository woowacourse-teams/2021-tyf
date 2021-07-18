import DefaultUserProfile from '../../../assets/images/defualtUserProfile.png';
import { NickName, ProfileImg, ProfileContainer } from './Profile.styles';
import { useParams } from 'react-router-dom';
import { ParamTypes } from '../../../App';
import useCreator from '../../../service/hooks/useCreator';

const Profile = () => {
  const { creatorId } = useParams<ParamTypes>();
  const { nickname } = useCreator(creatorId);

  return (
    <ProfileContainer>
      <a href={window.location.origin + `/creator/${creatorId}`}>
        <ProfileImg style={{ backgroundImage: `url(${DefaultUserProfile})` }}></ProfileImg>
      </a>
      <NickName>{nickname}</NickName>
    </ProfileContainer>
  );
};

export default Profile;
