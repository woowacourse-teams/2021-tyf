import { Creator } from '../../../types';
import { Name, ProfileImg, StyledCreatorCard } from './CreatorCard.styles';
import defaultUserProfile from '../../../assets/images/default-user-profile.png';

export interface CreatorCardProps {
  creator: Creator;
}

// TODO: 프로필 기능 구현시 적용
// <ProfileImg src={creator.profileImgSrc} alt={creator.nickname + '의 프로필 사진'} />

const CreatorCard = ({ creator }: CreatorCardProps) => {
  return (
    <StyledCreatorCard role="creator-card">
      <ProfileImg src={defaultUserProfile} alt={creator.nickname + '의 프로필 사진'} />
      <Name>{creator.nickname}</Name>
    </StyledCreatorCard>
  );
};

export default CreatorCard;
