import { MIN_WIDTH } from './../../constants/style';
import styled from 'styled-components';

import Container from '../@atom/Container/Container';
import IconButton from '../@atom/IconButton/IconButton';
import hamburgerImg from '../../assets/icons/hamburger-btn.svg';
import logoImg from '../../assets/images/logo.png';

export const StyledNavBar = styled(Container)`
  height: 5rem;
  border-bottom: 1px solid ${({ theme }) => theme.color.border};
  flex-direction: row;
  justify-content: space-between;
  padding: 0 1rem;
  min-width: ${MIN_WIDTH};
`;

export const HamburgerButton = styled(IconButton).attrs({
  src: hamburgerImg,
})``;

export const StyledLogo = styled(IconButton).attrs({
  src: logoImg,
})`
  width: 10rem;
  height: 2.5rem;
`;
