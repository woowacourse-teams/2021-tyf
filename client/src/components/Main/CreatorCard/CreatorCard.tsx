import { VFC } from 'react';

import { Creator } from '../../../types';
import { Name, ProfileImg, StyledCreatorCard } from './CreatorCard.styles';

export interface CreatorCardProps {
  creator: Creator;
}

const CreatorCard: VFC<CreatorCardProps> = ({ creator }) => {
  return (
    <StyledCreatorCard>
      <ProfileImg src={creator.profileImgSrc} alt={creator.name + '의 프로필 사진'} />
      <Name>{creator.name}</Name>
    </StyledCreatorCard>
  );
};

export default CreatorCard;
