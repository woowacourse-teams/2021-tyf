import Profile from '../../Profile/Profile';
import { Creator } from '../../../../types';
import { DescriptionContainer, StyledButton, StyledCreatorInfo } from './MobileCreatorInfo.styles';

interface Props {
  creator: Creator;
  isAdmin: boolean;
  shareUrl: () => void;
  popupDonationAmountPage: () => void;
}

const MobileCreatorInfo = ({ creator, isAdmin, shareUrl, popupDonationAmountPage }: Props) => {
  return (
    <StyledCreatorInfo>
      <Profile />
      <DescriptionContainer>
        <p>
          {creator.bio.split('\n').map((line) => (
            <>
              {line}
              <br />
            </>
          ))}
        </p>
      </DescriptionContainer>
      {isAdmin ? (
        <StyledButton onClick={shareUrl}>내 페이지 공유하기</StyledButton>
      ) : (
        <StyledButton onClick={popupDonationAmountPage}>도네이션하기</StyledButton>
      )}
    </StyledCreatorInfo>
  );
};

export default MobileCreatorInfo;
