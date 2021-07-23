import styled from 'styled-components';
import Button from '../../@atom/Button/Button';

export const StyledDesktopMenu = styled.div`
  position: fixed;
  top: 80px;
  right: 0;
  width: 100px;
  height: 100vh;
`;

export const MenuButton = styled.button`
  width: 100%;
  height: 80px;
  background-color: transparent;
  padding: 2rem 0;
  margin: 1rem 0;
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
  width: 2.25rem;
  height: 2.25rem;
  margin-bottom: 0.25rem;
`;
