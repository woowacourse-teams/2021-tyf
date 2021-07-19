import styled from 'styled-components';
import IconButton from '../../@atom/IconButton/IconButton';
import LogoImg from '../../../assets/images/logo.svg';

const Logo = styled(IconButton).attrs({
  src: LogoImg,
})`
  width: 8.5rem;
`;

export const FixedLogo = styled(IconButton).attrs({
  src: LogoImg,
})`
  width: 8.5rem;
  position: absolute;
  top: 1rem;
`;

export default Logo;
