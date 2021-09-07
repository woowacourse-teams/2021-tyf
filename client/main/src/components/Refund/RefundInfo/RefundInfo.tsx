import { InfoContainer, StyledRefundInfo, StyledSubTitle, InfoTitle } from './RefundInfo.styles';
import useRefundOrderDetail from '../../../service/refund/useRefundOrderDetail';
import { toCommaSeparatedString } from '../../../utils/format';

export interface RefundInfoProps {
  refundAccessToken: string;
}

const RefundInfo = ({ refundAccessToken }: RefundInfoProps) => {
  const { refundOrderDetail } = useRefundOrderDetail(refundAccessToken);

  return (
    <StyledRefundInfo>
      <StyledSubTitle>결제내역</StyledSubTitle>
      <InfoContainer>
        <p>
          <InfoTitle>충전포인트:</InfoTitle>
          {toCommaSeparatedString(refundOrderDetail.chargedPoint)} tp
        </p>
        <p>
          <InfoTitle>결제금액:</InfoTitle>
          {toCommaSeparatedString(refundOrderDetail.price)} 원
        </p>
        <p>
          <InfoTitle>결제일자:</InfoTitle>
          {String(refundOrderDetail.createdAt)}
        </p>
      </InfoContainer>
    </StyledRefundInfo>
  );
};

export default RefundInfo;
