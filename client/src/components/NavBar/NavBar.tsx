import { useHistory } from 'react-router-dom';
import useModal from '../../utils/useModal';
import Menu from '../Menu/Menu';
import { StyledNavBar, StyledLogo, HamburgerButton, LoginButton } from './NavBar.styles';

const NavBar = () => {
  const history = useHistory();
  const { isOpen, toggleModal, closeModal } = useModal();

  return (
    <>
      <StyledNavBar>
        <HamburgerButton onClick={() => toggleModal()} />
        <StyledLogo onClick={() => history.push('/')} />
        <LoginButton to="/login">로그인</LoginButton>
      </StyledNavBar>
      {isOpen && <Menu onClose={closeModal} />}
    </>
  );
};

export default NavBar;
