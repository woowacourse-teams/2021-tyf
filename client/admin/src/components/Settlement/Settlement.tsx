import React from 'react';
import { Button } from '../@atom/Button/Button.styles';

import {
  ItemContent,
  SettlementList,
  SettlementListItem,
  StyledSettlement,
  Title,
  Bold,
} from './Settlement.styles';

export const Settlement = () => {
  return (
    <StyledSettlement>
      <Title>정산신청목록</Title>
      <SettlementList>
        <SettlementListItem>
          <ItemContent>
            <Bold> 아이디</Bold> | inchinch
            <br />
            <Bold>닉네임</Bold> | 인치
            <br />
            <Bold>이름</Bold> | 한창희
            <br />
            <Bold>이메일</Bold> | hchayan196@gmail.com
            <br />
            <Bold>정산금액</Bold> | 122,000원
            <br />
            <Bold>정산신청일자</Bold> | 2021.08.09 (2일전)
            <br />
          </ItemContent>
          <Button>정산수락</Button>
        </SettlementListItem>
      </SettlementList>
    </StyledSettlement>
  );
};
