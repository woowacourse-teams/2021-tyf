import Profile from '../../Profile/Profile';
import { Creator } from '../../../../types';
import { DescriptionContainer, StyledButton, StyledCreatorInfo } from './MobileCreatorInfo.styles';
import React from 'react';

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
          {creator.bio.split('\n').map((line, index) => (
            <React.Fragment key={index}>
              {line}
              <br />
            </React.Fragment>
          ))}
        </p>
      </DescriptionContainer>
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
    </StyledCreatorInfo>
  );
};

export default MobileCreatorInfo;
