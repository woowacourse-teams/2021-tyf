import styled from 'styled-components';
import IconButton from '../../@atom/IconButton/IconButton';
import LogoImg from '../../../assets/images/logo.png';

const Logo = styled(IconButton).attrs({
  src: LogoImg,
})`
  width: 8.5rem;
`;

export const FixedLogo = styled(IconButton).attrs({
  src: LogoImg,
})`
  width: 8.5rem;
  position: fixed;
  top: 1rem;
`;

export default Logo;
