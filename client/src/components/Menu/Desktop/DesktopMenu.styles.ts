import styled, { keyframes } from 'styled-components';
import PALETTE from '../../../constants/palette';

const MenuOpen = keyframes`
  0% {
    transform: translateX(100%)
  }

  100% {
    transform: translateX(0)
  }
`;

export const StyledDesktopMenu = styled.div`
  position: fixed;
  top: 4rem;
  right: 0;
  width: 5.625rem;
  height: 100vh;
  background-color: ${PALETTE.WHITE_400};

  animation: ${MenuOpen} 0.5s ease-in-out;
`;

export const MenuButton = styled.button`
  background-color: transparent;
  width: 100%;
  height: 4.625rem;
  border: none;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  position: relative;
  font-size: 0.75rem;
  font-weight: 500;

  :hover::after {
    content: '';
    width: 0.25rem;
    height: 100%;
    background-color: ${({ theme }) => theme.primary.base};
    position: absolute;
    right: 0;
  }
`;

export const MenuIcon = styled.img`
  width: 1.5rem;
  height: 1.5rem;
  margin-bottom: 0.5rem;
`;
