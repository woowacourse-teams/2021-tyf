import { useEffect } from 'react';
import { useParams } from 'react-router-dom';

import { ParamTypes } from '../../../App';
import Transition from '../../../components/@atom/Transition/Transition.styles';
import { FixedLogo } from '../../../components/@molecule/Logo/Logo';
import useCreator from '../../../service//creator/useCreator';
import useDonation from '../../../service//donation/useDonation';
import { popupWindow } from '../../../utils/popup';
import { INVALID_DONATION_ID } from '../../../service/@state/donation';
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
import useAuthCheckEffect from '../../../service/@shared/useAuthCheckEffect';

const DonationSuccessPage = () => {
  const { creatorId } = useParams<ParamTypes>();
  const { nickname } = useCreator(creatorId);
  const { donation } = useDonation();

  usePageRefreshGuardEffect(creatorId, false, '/donation/' + creatorId);
  useAuthCheckEffect(window.close);

  const openCreatorPage = () => {
    popupWindow(window.location.origin + `/creator/${creatorId}`);
    window.close();
  };

  useEffect(() => {
    if (donation.donationId !== INVALID_DONATION_ID) return;

    window.close();
  }, [donation]);

  return (
    <StyledTemplate>
      <FixedLogo onClick={() => popupWindow(window.location.origin)} />
      <Transition>
        <SuccessMessageContainer>
          <SubText>{nickname}님에게</SubText>
          <MainText>{toCommaSeparatedString(donation.amount)}tp</MainText>
          <SubText>후원되었습니다.</SubText>
          <EmojiText>🎉</EmojiText>
        </SuccessMessageContainer>
      </Transition>
      <Transition>
        <SuccessButtonContainer>
          <CreatorRouteButton onClick={openCreatorPage}>
            🏠 창작자 페이지로 놀러가기
          </CreatorRouteButton>
          <CloseButton onClick={window.close}>창 닫기</CloseButton>
        </SuccessButtonContainer>
      </Transition>
    </StyledTemplate>
  );
};

export default DonationSuccessPage;
