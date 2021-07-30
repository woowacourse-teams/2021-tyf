import { Creator } from '../../../types';
import { Name, ProfileImg, StyledCreatorCard, Bio } from './CreatorCard.styles';
import defaultUserProfile from '../../../assets/images/default-user-profile.png';
import { getRandomNumber } from '../../../utils/random';
import { useEffect, useState } from 'react';

export interface CreatorCardProps {
  creator: Creator;
}

const CARD_COLORS = [
  '#55efc4',
  '#81ecec',
  '#74b9ff',
  '#a29bfe',
  '#636e72',
  '#ffeaa7',
  '#fab1a0',
  '#ff7675',
  '#fd79a8',
];

const CreatorCard = ({ creator }: CreatorCardProps) => {
  const [colorNumber, setColor] = useState(0);

  useEffect(() => {
    setColor(getRandomNumber(0, 8));
  }, []);

  return (
    <StyledCreatorCard role="creator-card" color={CARD_COLORS[colorNumber]}>
      <ProfileImg
        src={creator.profileImage ?? defaultUserProfile}
        alt={creator.nickname + '의 프로필 사진'}
      />
      <Name>{creator.nickname}</Name>
      <Bio>{creator.bio ?? `반갑습니다! ${creator.nickname}입니다!`}</Bio>
    </StyledCreatorCard>
  );
};

export default CreatorCard;
