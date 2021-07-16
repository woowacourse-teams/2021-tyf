import { useHistory } from 'react-router-dom';
import Anchor from '../@atom/Anchor/Anchor';
import { StyledNavBar, StyledLogo, HamburgerButton, LoginButton } from './NavBar.styles';

const NavBar = () => {
  const history = useHistory();

  return (
    <StyledNavBar>
      <HamburgerButton />
      <StyledLogo onClick={() => history.push('/')} />
      <LoginButton to="/login">로그인</LoginButton>
    </StyledNavBar>
  );
};

export default NavBar;
