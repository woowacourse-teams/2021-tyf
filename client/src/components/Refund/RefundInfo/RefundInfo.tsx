import { InfoContainer, StyledRefundInfo, StyledSubTitle, InfoTitle } from './RefundInfo.styles';

const RefundInfo = () => {
  return (
    <StyledRefundInfo>
      <StyledSubTitle>주문내역</StyledSubTitle>
      <InfoContainer>
        <p>
          <InfoTitle>창작자명: </InfoTitle>인치
        </p>
        <p>
          <InfoTitle>후원금액: </InfoTitle>1,200원
        </p>
        <p>
          <InfoTitle>후원일자: </InfoTitle>2021.08.03
        </p>
      </InfoContainer>
    </StyledRefundInfo>
  );
};

export default RefundInfo;
