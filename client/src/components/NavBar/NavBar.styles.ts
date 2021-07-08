import styled from 'styled-components';
import PALETTE from '../../constants/palette';

import Container from '../@atom/Container/Container';
import IconButton from '../@atom/IconButton/IconButton';
import hamburgerImg from '../../assets/icons/hamburger-btn.svg';
import logoImg from '../../assets/images/logo.png';

export const StyledNavBar = styled(Container)`
  height: 5rem;
  border-bottom: 1px solid ${PALETTE.GRAY_200};
  flex-direction: row;
  justify-content: space-between;
  padding: 0 1rem;
`;

export const HamburgerButton = styled(IconButton).attrs({
  src: hamburgerImg,
})``;

export const StyledLogo = styled(IconButton).attrs({
  src: logoImg,
})`
  width: 8.75rem;
  height: 2rem;
`;
