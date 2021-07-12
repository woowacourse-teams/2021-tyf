import { VFC } from 'react';

import {
  CloseButton,
  CreatorRouteButton,
  EmojiText,
  MainText,
  StyledTemplate,
  SubText,
} from './DonationSuccessPage.styles';

const DonationSuccessPage: VFC = () => {
  return (
    <StyledTemplate>
      <SubText>파노님에게</SubText>
      <MainText>3,000원</MainText>
      <SubText>후원되었습니다.</SubText>
      <EmojiText>🎉</EmojiText>
      <CreatorRouteButton>🏠 창작자 페이지로 놀러가기</CreatorRouteButton>
      <CloseButton>창 닫기</CloseButton>
    </StyledTemplate>
  );
};

export default DonationSuccessPage;
