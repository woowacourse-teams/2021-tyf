import styled from 'styled-components';

import PALETTE from '../../constants/palette';
import { Button } from '../@atom/Button/Button.styles';

export const StyledSettlement = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const Title = styled.h2`
  margin-bottom: 2rem;
`;

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
  font-weight: 700;
  min-width: 7.5rem;
  text-align: end;
`;

export const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  height: 7rem;
`;

export const AgreeButton = styled(Button)`
  background-color: ${PALETTE.GREEN_700};

  &:hover,
  &:focus {
    background-color: ${PALETTE.GREEN_900};
  }
`;

export const DeclineButton = styled(Button)``;

export const EmptyContent = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
