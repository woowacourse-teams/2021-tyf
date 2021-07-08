import Anchor from '../@atom/Anchor/Anchor';
import { StyledNavBar, StyledLogo, HamburgerButton } from './NavBar.styles';

const NavBar = () => {
  return (
    <StyledNavBar>
      <HamburgerButton />
      <StyledLogo />
      <Anchor>LOGIN</Anchor>
    </StyledNavBar>
  );
};

export default NavBar;
