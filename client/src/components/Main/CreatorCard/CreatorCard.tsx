import { VFC } from 'react';

import { Creator } from '../../../types';
import { Name, ProfileImg, StyledCreatorCard } from './CreatorCard.styles';

export interface CreatorCardProps {
  creator: Creator;
}

const CreatorCard: VFC<CreatorCardProps> = ({ creator }) => {
  return (
    <StyledCreatorCard role="creator-card">
      <ProfileImg src={creator.profileImgSrc} alt={creator.nickname + '의 프로필 사진'} />
      <Name>{creator.nickname}</Name>
    </StyledCreatorCard>
  );
};

export default CreatorCard;
