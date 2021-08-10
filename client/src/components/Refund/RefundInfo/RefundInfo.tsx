import { InfoContainer, StyledRefundInfo, StyledSubTitle, InfoTitle } from './RefundInfo.styles';
import useRefundOrderDetail from '../../../service//refund/useRefundOrderDetail';

interface RefundInfoProps {
  refundAccessToken: string;
}

const RefundInfo = ({ refundAccessToken }: RefundInfoProps) => {
  const { refundOrderDetail } = useRefundOrderDetail(refundAccessToken);

  return (
    <StyledRefundInfo>
      <StyledSubTitle>주문내역</StyledSubTitle>
      <InfoContainer>
        <p>
          <InfoTitle>창작자명:</InfoTitle>
          {refundOrderDetail.creator.nickname}
        </p>
        <p>
          <InfoTitle>후원금액:</InfoTitle>
          {refundOrderDetail.donation.amount}
        </p>
        <p>
          <InfoTitle>후원일자:</InfoTitle>
          {refundOrderDetail.donation.createdAt}
        </p>
      </InfoContainer>
      <InfoContainer>
        <p>
          <InfoTitle>후원자명:</InfoTitle>
          {refundOrderDetail.donation.name}
        </p>
        <p>
          <InfoTitle>후원메시지:</InfoTitle>
          {refundOrderDetail.donation.message}
        </p>
      </InfoContainer>
    </StyledRefundInfo>
  );
};

export default RefundInfo;
