import { VFC } from 'react';

import {
  CloseButton,
  CreatorRouteButton,
  EmojiText,
  MainText,
  StyledTemplate,
  SubText,
  SuccessButtonContainer,
  SuccessMessageContainer,
} from './DonationSuccessPage.styles';

const DonationSuccessPage: VFC = () => {
  return (
    <StyledTemplate>
      <SuccessMessageContainer>
        <SubText>파노님에게</SubText>
        <MainText>3,000원</MainText>
        <SubText>후원되었습니다.</SubText>
        <EmojiText>🎉</EmojiText>
      </SuccessMessageContainer>
      <SuccessButtonContainer>
        <CreatorRouteButton>🏠 창작자 페이지로 놀러가기</CreatorRouteButton>
        <CloseButton>창 닫기</CloseButton>
      </SuccessButtonContainer>
    </StyledTemplate>
  );
};

export default DonationSuccessPage;
