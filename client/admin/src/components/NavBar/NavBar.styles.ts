import styled from 'styled-components';
import { SIZE } from '../../constants/device';
import PALETTE from '../../constants/palette';

export const StyledNavBar = styled.nav`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 0 2rem;
  align-items: center;
  background-color: ${PALETTE.WHITE_400};
  height: 5rem;
  min-width: ${SIZE.DESKTOP_LARGE}px;
`;

export const StyledTitle = styled.h3``;

export const Menu = styled.div`
  display: flex;
`;

export const MenuItem = styled.span`
  padding-left: 1rem;
  cursor: pointer;

  &:hover {
    font-weight: 700;
  }
`;
