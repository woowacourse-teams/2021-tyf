import { Button } from '../@atom/Button/Button.styles';
import {
  Bold,
  ItemContent,
  RefundList,
  RefundListItem,
  StyledRefund,
  Title,
} from './Refund.styles';

export const Refund = () => {
  return (
    <StyledRefund>
      <Title>환불신청목록</Title>
      <RefundList>
        <RefundListItem>
          <ItemContent>
            <Bold>주문번호</Bold> | k3khjas-23ca-gcvqs
            <br />
            <Bold>후원받은 창작자명</Bold> | 파노
            <br />
            <Bold>후원자명</Bold> | 인치
            <br />
            <Bold>후원자 이메일</Bold> | hchayan196@gmail.com
            <br />
            <Bold>후원금액</Bold> | 12,000원
            <br />
            <Bold>후원일자</Bold> | 2021.08.09 (2일전)
            <br />
          </ItemContent>
          <Button>환불수락</Button>
        </RefundListItem>
      </RefundList>
    </StyledRefund>
  );
};
