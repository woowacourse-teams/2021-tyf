import styled, { css } from 'styled-components';

import Container from '../@atom/Container/Container.styles';
import IconButton from '../@atom/IconButton/IconButton';
import logoImg from '../../assets/images/logo.png';
import Anchor from '../@atom/Anchor/Anchor';
import { DEVICE, SIZE } from '../../constants/device';
import TextButton from '../@atom/TextButton/TextButton.styles';
import { Z_INDEX } from '../../constants/style';
import PALETTE from '../../constants/palette';
import DropDown from '../../assets/icons/drop-down.svg';

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
  align-items: center;
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
  height: auto;

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
  margin-right: 0.75rem;

  @media ${DEVICE.DESKTOP_LARGE} {
    font-size: 1.125rem;
    white-space: nowrap;
    max-width: 10rem;
    /* overflow: auto; */
    overflow: hidden;
  }
`;

export const StyledPoint = styled(TextButton)`
  display: none;
  width: fit-content;

  @media ${DEVICE.DESKTOP_LARGE} {
    display: block;
    font-size: 1.125rem;
    white-space: nowrap;
    max-width: 10rem;
    overflow: auto;
  }

  &:hover {
    font-weight: 500;
  }

  &:after {
    content: '|';
    margin: 0 0.5rem;
    color: ${PALETTE.GRAY_400};
  }
`;

const UpSide = css`
  transform: rotate(180deg);
  top: 0;
`;

interface DropDownIconProps {
  isOpen: boolean;
}

export const DropDownIcon = styled.img.attrs({ src: DropDown })<DropDownIconProps>`
  position: relative;
  width: 1rem;
  height: 1rem;
  top: 0.25rem;

  ${({ isOpen }) => isOpen && UpSide}
  transition: all 0.4s ease-in-out;
`;
