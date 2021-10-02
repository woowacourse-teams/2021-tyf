import DefaultProfileImg from '../../../../assets/images/default-user-profile.png';
import { Creator } from '../../../../types';
import {
  DescriptionContainer,
  InfoContainer,
  NickName,
  ProfileImg,
  StyledCreatorInfo,
  StyledInfo,
  StyledButton,
} from './DesktopCreatorInfo.styles';

interface Props {
  creator: Creator;
  isAdmin: boolean;
  shareUrl: () => void;
  popupDonationAmountPage: () => void;
}

const DesktopCreatorInfo = ({ creator, isAdmin, shareUrl, popupDonationAmountPage }: Props) => {
  return (
    <StyledCreatorInfo>
      <ProfileImg alt="profile" src={creator.profileImage || DefaultProfileImg} />
      <InfoContainer>
        <StyledInfo>
          <NickName>{creator.nickname}</NickName>
          {isAdmin ? (
            <StyledButton onClick={shareUrl}>후원링크 공유하기</StyledButton>
          ) : (
            <StyledButton onClick={popupDonationAmountPage}>도네이션하기</StyledButton>
          )}
        </StyledInfo>

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
      </InfoContainer>
    </StyledCreatorInfo>
  );
};

export default DesktopCreatorInfo;
