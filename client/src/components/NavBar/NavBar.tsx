import { useHistory } from 'react-router-dom';
import useLoginUserInfo from '../../service/hooks/useLoginUserInfo';
import useModal from '../../utils/useModal';
import Anchor from '../@atom/Anchor/Anchor';
import Menu from '../Menu/Menu';
import { StyledNavBar, StyledLogo, HamburgerButton, LoginButton } from './NavBar.styles';

const NavBar = () => {
  const history = useHistory();
  const { isOpen, toggleModal, closeModal } = useModal();
  const { userInfo } = useLoginUserInfo();

  return (
    <>
      <StyledNavBar>
        <HamburgerButton onClick={() => toggleModal()} />
        <StyledLogo onClick={() => history.push('/')} />
        {userInfo ? (
          <Anchor to={`/creator/${userInfo.pageName}`}>{userInfo.nickname}</Anchor>
        ) : (
          <LoginButton to="/login">로그인</LoginButton>
        )}
      </StyledNavBar>
      {isOpen && <Menu onClose={closeModal} />}
    </>
  );
};

export default NavBar;
