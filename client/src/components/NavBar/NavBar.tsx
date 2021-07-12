import { StyledNavBar, StyledLogo, HamburgerButton, LoginButton } from './NavBar.styles';

const NavBar = () => {
  return (
    <StyledNavBar>
      <HamburgerButton />
      <StyledLogo />
      <LoginButton to="/login">로그인</LoginButton>
    </StyledNavBar>
  );
};

export default NavBar;
