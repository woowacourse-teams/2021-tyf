import useSettlement from '../../../../service/settlement/useSettlement';
import useUserInfo from '../../../../service//user/useUserInfo';
import { toCommaSeparatedString } from '../../../../utils/format';
import Button from '../../../@atom/Button/Button.styles';
import {
  InfoContainer,
  StyledSubTitle,
  Caution,
  StyledCarousel,
  Amount,
  StyledSettlementInfo,
  AmountContainer,
  StyledCommonSubTitle,
} from './MobileSettlementInfo styles';

const MobileSettlementInfo = () => {
  const { userInfo } = useUserInfo();
  const { exchangeablePoint, exchangedTotalPoint, currentPoint, applySettlement } = useSettlement();
  const nextMonth = Math.floor(new Date().getMonth() % 12) + 2; // TODO: 달계산 알고리즘 다시 작성

  return (
    <StyledSettlementInfo>
      <StyledCommonSubTitle>{userInfo?.nickname}님이</StyledCommonSubTitle>
      <StyledCarousel pageCount={3}>
        <InfoContainer>
          <StyledSubTitle>현재 도네이션 받은 금액은</StyledSubTitle>
          <AmountContainer>
            <Amount>{toCommaSeparatedString(currentPoint ?? 0)}</Amount>원
          </AmountContainer>
          <Caution>10,000tp 이상부터 정산이 가능합니다.</Caution>
        </InfoContainer>
        <InfoContainer>
          <StyledSubTitle>정산 받을 수 있는 금액은</StyledSubTitle>
          <AmountContainer>
            <Amount>{toCommaSeparatedString(exchangeablePoint ?? 0)}</Amount>원
          </AmountContainer>
          <Caution>오늘 요청시 환급일 2021 / {nextMonth} / 7</Caution>
        </InfoContainer>
        <InfoContainer>
          <StyledSubTitle>현재까지 정산 받은 금액은</StyledSubTitle>
          <AmountContainer>
            <Amount>{toCommaSeparatedString(exchangedTotalPoint ?? 0)}</Amount>원
          </AmountContainer>
          <Caution>Thank You For 에서 정산받은 총 금액입니다</Caution>
        </InfoContainer>
      </StyledCarousel>
      <Button onClick={applySettlement}>정산 신청하기</Button>
    </StyledSettlementInfo>
  );
};

export default MobileSettlementInfo;
