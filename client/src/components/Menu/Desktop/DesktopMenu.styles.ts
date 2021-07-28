import styled from 'styled-components';
import PALETTE from '../../../constants/palette';

export const StyledDesktopMenu = styled.div`
  position: fixed;
  top: 4rem;
  right: 0;
  width: 100px;
  height: 100vh;
  background-color: ${PALETTE.WHITE_400};
`;

export const MenuButton = styled.button`
  background-color: transparent;
  width: 100%;
  height: 80px;
  padding: 2rem 0;
  margin: 0.5rem 0;
  border: none;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  cursor: pointer;
  position: relative;

  :hover::after {
    content: '';
    width: 0.375rem;
    height: 100%;
    background-color: ${({ theme }) => theme.primary.base};
    position: absolute;
    right: 0;
    border-radius: 0.25rem 0 0 0.25rem;
  }
`;

export const MenuIcon = styled.img`
  width: 1.75rem;
  height: 1.75rem;
  margin-bottom: 0.5rem;
`;
