import styled from 'styled-components';

import { MIN_WIDTH } from './../../constants/style';
import Container from '../@atom/Container/Container';
import IconButton from '../@atom/IconButton/IconButton';
import hamburgerImg from '../../assets/icons/hamburger.svg';
import logoImg from '../../assets/images/logo.svg';
import Anchor from '../@atom/Anchor/Anchor';

export const StyledNavBar = styled(Container)`
  height: 3.5rem;
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
  flex-direction: row;
  justify-content: space-between;
  padding: 0 1rem;
  min-width: ${MIN_WIDTH};
  position: relative;
`;

export const HamburgerButton = styled(IconButton).attrs({
  src: hamburgerImg,
})`
  width: 1.5rem;
  height: 1.5rem;
`;

export const StyledLogo = styled(IconButton).attrs({
  src: logoImg,
})`
  width: 8.5rem;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
`;

export const LoginButton = styled(Anchor)`
  font-size: 0.875rem;
  font-weight: 400;
`;
