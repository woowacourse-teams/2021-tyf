import { Creator } from '../../../types';
import { Name, ProfileImg, StyledCreatorCard } from './CreatorCard.styles';
import defaultUserProfile from '../../../assets/images/defaultUserProfile.png';

export interface CreatorCardProps {
  creator: Creator;
}

const CreatorCard = ({ creator }: CreatorCardProps) => {
  return (
    <StyledCreatorCard role="creator-card">
      {/* <ProfileImg src={creator.profileImgSrc} alt={creator.nickname + '의 프로필 사진'} /> */}
      <ProfileImg src={defaultUserProfile} alt={creator.nickname + '의 프로필 사진'} />
      <Name>{creator.nickname}</Name>
    </StyledCreatorCard>
  );
};

export default CreatorCard;
