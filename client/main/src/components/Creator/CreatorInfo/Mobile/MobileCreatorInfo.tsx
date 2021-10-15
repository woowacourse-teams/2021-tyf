import Profile from '../../Profile/Profile';
import { Creator } from '../../../../types';
import { DescriptionContainer, StyledButton, StyledCreatorInfo } from './MobileCreatorInfo.styles';

interface Props {
  creator: Creator;
  isAdmin: boolean;
  bankRegistered: boolean;
  shareUrl: () => void;
  popupDonationAmountPage: () => void;
}

const MobileCreatorInfo = ({
  creator,
  isAdmin,
  shareUrl,
  bankRegistered,
  popupDonationAmountPage,
}: Props) => {
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
        <StyledButton onClick={shareUrl}>도네이션 주소 공유하기</StyledButton>
      ) : (
        <StyledButton onClick={popupDonationAmountPage} disabled={!bankRegistered}>
          도네이션하기
        </StyledButton>
      )}
    </StyledCreatorInfo>
  );
};

export default MobileCreatorInfo;
