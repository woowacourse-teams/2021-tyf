import styled from 'styled-components';

import IconButton from '../../@atom/IconButton/IconButton';
import LogoImg from '../../../assets/images/logo.png';
import { DEVICE } from '../../../constants/device';

const Logo = styled.img.attrs({
  src: LogoImg,
  alt: 'logo',
})`
  width: 8.5rem;

  @media ${DEVICE.DESKTOP_LARGE} {
    width: 16rem;
  }
`;

export const FixedLogo = styled(IconButton).attrs({
  src: LogoImg,
})`
  width: 8.5rem;
  position: absolute;
  top: 1rem;

  @media ${DEVICE.DESKTOP_LARGE} {
    width: 12rem;
  }
`;

export default Logo;
