import useSettlement from '../../../../service//user/useSettlement';
import useUserInfo from '../../../../service//user/useUserInfo';
import { toCommaSeparatedString } from '../../../../utils/format';
import Button from '../../../@atom/Button/Button.styles';
import {
  InfoContainer,
  StyledSubTitle,
  Caution,
  Amount,
  StyledSettlementInfo,
  AmountContainer,
} from './DesktopSettlementInfo styles';

const DesktopSettlementInfo = () => {
  const { donationAmount, settlableAmount, settledAmount, applySettlement } = useSettlement();
  const nextMonth = new Date().getMonth() + 1;

  return (
    <StyledSettlementInfo>
      <InfoContainer>
        <StyledSubTitle>현재까지 정산 받은 금액은</StyledSubTitle>
        <AmountContainer>
          <span>
            <Amount>{toCommaSeparatedString(settledAmount)}</Amount>원
          </span>
          <Caution>Thank You For 에서 정산받은 총 금액입니다</Caution>
        </AmountContainer>
        <Button disabled>정산 내역 확인하기</Button>
      </InfoContainer>
      <InfoContainer>
        <StyledSubTitle>현재 도네이션 받은 금액은</StyledSubTitle>
        <AmountContainer>
          <span>
            <Amount>{toCommaSeparatedString(donationAmount)}</Amount>원
          </span>
          <Caution>도네이션 받은 날짜 기준 7일 후 정산이 가능합니다.</Caution>
        </AmountContainer>
        <Button disabled>도네이션 내역 확인하기</Button>
      </InfoContainer>
      <InfoContainer>
        <StyledSubTitle>정산 받을 수 있는 금액은</StyledSubTitle>
        <AmountContainer>
          <span>
            <Amount>{toCommaSeparatedString(settlableAmount)}</Amount>원
          </span>
          <Caution>오늘 요청시 환급일 2021/{nextMonth}/28</Caution>
        </AmountContainer>
        <Button onClick={applySettlement}>정산 신청하기</Button>
      </InfoContainer>
    </StyledSettlementInfo>
  );
};

export default DesktopSettlementInfo;
