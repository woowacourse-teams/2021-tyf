import React from 'react';
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
  bankRegistered: boolean;
  shareUrl: () => void;
  popupDonationAmountPage: () => void;
}

const DesktopCreatorInfo = ({
  creator,
  isAdmin,
  bankRegistered,
  shareUrl,
  popupDonationAmountPage,
}: Props) => {
  return (
    <StyledCreatorInfo>
      <ProfileImg alt="profile" src={creator.profileImage || DefaultProfileImg} />
      <InfoContainer>
        <StyledInfo>
          <NickName>{creator.nickname}</NickName>
          {isAdmin ? (
            <StyledButton onClick={shareUrl}>도네이션 주소 공유하기</StyledButton>
          ) : (
            <StyledButton
              onClick={popupDonationAmountPage}
              disabled={!bankRegistered}
              title={!bankRegistered ? '아직 계좌인증을 받지 못했습니다.' : '도네이션 하기'}
            >
              도네이션하기
            </StyledButton>
          )}
        </StyledInfo>

        <DescriptionContainer>
          <p>
            {creator.bio.split('\n').map((line, index) => (
              <React.Fragment key={index}>
                {line}
                <br />
              </React.Fragment>
            ))}
          </p>
        </DescriptionContainer>
      </InfoContainer>
    </StyledCreatorInfo>
  );
};

export default DesktopCreatorInfo;
