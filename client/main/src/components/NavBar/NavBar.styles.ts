import styled, { css } from 'styled-components';

import Container from '../@atom/Container/Container.styles';
import IconButton from '../@atom/IconButton/IconButton';
import logoImg from '../../assets/images/logo.svg';
import Anchor from '../@atom/Anchor/Anchor';
import { DEVICE, SIZE } from '../../constants/device';
import TextButton from '../@atom/TextButton/TextButton.styles';
import { Z_INDEX } from '../../constants/style';

const NavBarHeightStyle = css`
  height: 3.5rem;
  z-index: ${Z_INDEX.FOREGROUND};

  @media ${DEVICE.DESKTOP} {
    height: 4rem;
  }
`;

export const StyledNavBar = styled(Container)`
  ${NavBarHeightStyle}
  position: fixed;
  background-color: white;
  padding: 0 1rem;
  flex-direction: row;
  justify-content: flex-end;
  min-width: ${SIZE.MOBILE_MIN}px;
  position: fixed;
  width: 100vw;
  background-color: white;

  @media ${DEVICE.DESKTOP} {
    padding: 0 2rem;
  }
`;

export const NavBarArea = styled.div`
  ${NavBarHeightStyle}
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
  width: fit-content;

  @media ${DEVICE.DESKTOP} {
    font-size: 1.125rem;
    max-width: 8rem;
  }
`;
