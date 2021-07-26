import styled from 'styled-components';

import Container from '../@atom/Container/Container';
import IconButton from '../@atom/IconButton/IconButton';
import logoImg from '../../assets/images/logo.svg';
import Anchor from '../@atom/Anchor/Anchor';
import { DEVICE, SIZE } from '../../constants/device';
import TextButton from '../@atom/TextButton/TextButton.styles';

export const StyledNavBar = styled(Container)`
  height: 3.5rem;
  flex-direction: row;
  justify-content: flex-end;
  padding: 0 1rem;
  min-width: ${SIZE.MOBILE_MIN}px;
  /* position: fixed;
  width: 100vw; */
  background-color: white;

  @media ${DEVICE.DESKTOP} {
    height: 4rem;
    padding: 0 2rem;
  }
`;

export const StyledLogo = styled(IconButton).attrs({
  src: logoImg,
})`
  width: 8.5rem;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);

  @media ${DEVICE.DESKTOP} {
    width: 10rem;
  }
`;

export const LoginButton = styled(Anchor)`
  font-size: 0.875rem;
  font-weight: 400;

  @media ${DEVICE.DESKTOP} {
    font-size: 1rem;
  }
`;

export const StyledTextButton = styled(TextButton)`
  @media ${DEVICE.DESKTOP} {
    font-size: 1.125rem;
  }
`;
