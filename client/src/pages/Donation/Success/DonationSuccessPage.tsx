import { useEffect } from 'react';
import { useParams } from 'react-router-dom';

import { ParamTypes } from '../../../App';
import { FixedLogo } from '../../../components/@molecule/Logo/Logo';
import useCreator from '../../../service/hooks/creator/useCreator';
import useDonation from '../../../service/hooks/donation/useDonation';
import { popupWindow } from '../../../service/popup';
import { INVALID_DONATION_ID } from '../../../service/state/donation';
import { toCommaSeparatedString } from '../../../utils/format';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';
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

  usePageRefreshGuardEffect(creatorId, false, '/donation/' + creatorId);

  const openCreatorPage = () => {
    popupWindow(window.location.origin + `/creator/${creatorId}`);
    window.close();
  };

  const closeWindow = () => {
    window.close();
  };

  useEffect(() => {
    if (donation.donationId !== INVALID_DONATION_ID) return;

    closeWindow();
  }, [donation]);

  return (
    <StyledTemplate>
      <FixedLogo onClick={() => popupWindow(window.location.origin)} />
      <SuccessMessageContainer>
        <SubText>{nickname}님에게</SubText>
        <MainText>{toCommaSeparatedString(donation.amount)}원</MainText>
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
