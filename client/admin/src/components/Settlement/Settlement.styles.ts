import styled from 'styled-components';

import PALETTE from '../../constants/palette';

export const StyledSettlement = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const Title = styled.h2``;

export const SettlementList = styled.ul`
  list-style: none;
  margin: 0;
  padding: 0;
  width: 100%;
`;

export const SettlementListItem = styled.li`
  font-size: 0.875rem;
  padding: 1rem 0;
  border-bottom: 1px solid ${PALETTE.GRAY_300};
  display: flex;
  justify-content: space-between;
  align-items: center;
  button {
    width: 5rem;
  }
`;

export const ItemContent = styled.div``;

export const Bold = styled.span`
  display: inline-block;
  font-weight: 600;
  min-width: 7.5rem;
  text-align: end;
`;
