import { useEffect } from 'react';
import { useParams } from 'react-router-dom';

import { ParamTypes } from '../../../App';
import { FixedLogo } from '../../../components/@molecule/Logo/Logo';
import useCreator from '../../../service/hooks/useCreator';
import useDonation from '../../../service/hooks/useDonation';
import { popupWindow } from '../../../service/popup';
import { INVALID_DONATION_ID } from '../../../service/state/donation';
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

const DonationSuccessPage = () => {
  const { creatorId } = useParams<ParamTypes>();
  const { nickname } = useCreator(creatorId);
  const { donation } = useDonation(creatorId);

  const openCreatorPage = () => {
    popupWindow(`/creator/${creatorId}`);
    window.close();
  };

  // TODO: 직접 url접근시 window.close 먹통됨 -> 창이 안닫힘

  const closeWindow = () => {
    window.close();
  };

  useEffect(() => {
    if (donation.donationId !== INVALID_DONATION_ID) return;

    closeWindow();
  }, [donation]);

  return (
    <StyledTemplate>
      <FixedLogo onClick={() => popupWindow('/')} />
      <SuccessMessageContainer>
        <SubText>{nickname}님에게</SubText>
        <MainText>{donation.amount.toLocaleString('en-us')}원</MainText>
        <SubText>후원되었습니다.</SubText>
        <EmojiText>🎉</EmojiText>
      </SuccessMessageContainer>
      <SuccessButtonContainer>
        <CreatorRouteButton onClick={openCreatorPage}>
          🏠 창작자 페이지로 놀러가기
        </CreatorRouteButton>
        <CloseButton onClick={closeWindow}>창 닫기</CloseButton>
      </SuccessButtonContainer>
    </StyledTemplate>
  );
};

export default DonationSuccessPage;
