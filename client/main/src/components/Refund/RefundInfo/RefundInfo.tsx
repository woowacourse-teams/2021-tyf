import { InfoContainer, StyledRefundInfo, StyledSubTitle, InfoTitle } from './RefundInfo.styles';
import useRefundOrderDetail from '../../../service/refund/useRefundOrderDetail';

export interface RefundInfoProps {
  refundAccessToken: string;
}

const RefundInfo = ({ refundAccessToken }: RefundInfoProps) => {
  // const { refundOrderDetail } = useRefundOrderDetail(refundAccessToken);

  return (
    <StyledRefundInfo>
      <StyledSubTitle>결제내역</StyledSubTitle>
      <InfoContainer>
        <p>
          <InfoTitle>충전포인트:</InfoTitle>
          5,500 tp
        </p>
        <p>
          <InfoTitle>결제금액:</InfoTitle>
          5,000 원
        </p>
        <p>
          <InfoTitle>결제일자:</InfoTitle>
          2021.08.03_17:24
        </p>
      </InfoContainer>
    </StyledRefundInfo>
  );
};

export default RefundInfo;
