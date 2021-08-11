import useSettlement from '../../service/useSettlement';
import {
  ItemContent,
  SettlementList,
  SettlementListItem,
  StyledSettlement,
  Title,
  Bold,
  ButtonContainer,
  AgreeButton,
  DeclineButton,
} from './Settlement.styles';

export const Settlement = () => {
  const { exchangeList, agreeExchange, declineExchange } = useSettlement();

  return (
    <StyledSettlement>
      <Title>정산신청목록</Title>
      <SettlementList>
        {exchangeList.map((item, index) => (
          <SettlementListItem key={index}>
            <ItemContent>
              <Bold> 아이디</Bold> | {item.pageName}
              <br />
              <Bold>닉네임</Bold> | {item.nickname}
              <br />
              <Bold>이름</Bold> | 한창희
              <br />
              <Bold>계좌번호</Bold> | {item.accountNumber}
              <br />
              <Bold>정산금액</Bold> | {item.exchangeAmount}원
              <br />
              <Bold>정산신청일자</Bold> | {item.createdAt}
              <br />
            </ItemContent>
            <ButtonContainer>
              <AgreeButton onClick={() => agreeExchange(item.pageName)}>정산수락</AgreeButton>
              <DeclineButton onClick={() => declineExchange(item.pageName)}>정산거부</DeclineButton>
            </ButtonContainer>
          </SettlementListItem>
        ))}
      </SettlementList>
    </StyledSettlement>
  );
};
