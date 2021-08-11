import { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import {
  requestAgreeExchange,
  requestDeclineExchange,
  requestExchangeList,
} from '../request/request';

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

interface Exchange {
  exchangeAmount: number;
  accountNumber: string;
  nickname: string;
  pageName: string;
  createdAt: string;
}

interface ExchangeListResponse {
  data?: Exchange[];
  errors?: Array<{ message: string }>;
}

export const Settlement = () => {
  const history = useHistory();
  const [exchangeList, setExchangeList] = useState<Exchange[]>([]);
  const [accessToken, setAccessToken] = useState('');

  const getAccessToken = () => {
    const token = sessionStorage.getItem('adminToken');
    if (!token) {
      alert('로그인 후 접근가능합니다.');
      // history.push('/');
      return;
    }

    setAccessToken(token);
  };

  const getExchangeList = async () => {
    try {
      const { data }: ExchangeListResponse = await requestExchangeList(accessToken);

      setExchangeList(data!);
    } catch (error) {
      // alert(error.message ?? error.data.message);
    }
  };

  const agreeExchange = async (pageName: string) => {
    try {
      await requestAgreeExchange(pageName, accessToken);

      alert('정산을 수락했습니다.');
    } catch (error) {
      alert(error.message);
    }
  };

  const declineExchange = async (pageName: string) => {
    try {
      const reason = window.prompt('거절 사유를 적어주세요');
      if (!reason) {
        throw Error('거절 사유가 작성되지 않았습니다.');
      }

      await requestDeclineExchange(pageName, reason, accessToken);

      alert('정산을 거절했습니다.');
    } catch (error) {
      alert(error.message);
    }
  };

  useEffect(() => {
    getAccessToken();
    getExchangeList();
  }, []);
  return (
    <StyledSettlement>
      <Title>정산신청목록</Title>
      <SettlementList>
        <SettlementListItem>
          <ItemContent>
            <Bold> 아이디</Bold> | 테스트
            <br />
            <Bold>닉네임</Bold> | 테스트
            <br />
            <Bold>이름</Bold> | 한창희 (이랑 계좌사진은 어케보여줄가?)
            <br />
            <Bold>계좌번호</Bold> | 테스트
            <br />
            <Bold>정산금액</Bold> | 11111원
            <br />
            <Bold>정산신청일자</Bold> | 테스트
            <br />
          </ItemContent>
          <ButtonContainer>
            <AgreeButton>정산수락</AgreeButton>
            <DeclineButton onClick={() => declineExchange('테스트')}>정산거부</DeclineButton>
          </ButtonContainer>
        </SettlementListItem>
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
