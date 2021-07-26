import { useHistory } from 'react-router-dom';

import useUserInfo from '../../service/hooks/useUserInfo';
import useModal from '../../utils/useModal';
import Menu from '../Menu/Menu';
import {
  StyledNavBar,
  StyledLogo,
  LoginButton,
  StyledTextButton,
  NavBarArea,
} from './NavBar.styles';

const NavBar = () => {
  const history = useHistory();
  const { isOpen, toggleModal, closeModal } = useModal();
  const { userInfo } = useUserInfo();

  return (
    <>
      <StyledNavBar>
        <StyledLogo onClick={() => history.push('/')} />
        {userInfo ? (
          <StyledTextButton onClick={toggleModal}>{userInfo.nickname}</StyledTextButton>
        ) : (
          <LoginButton to="/login">로그인</LoginButton>
        )}
      </StyledNavBar>
      <NavBarArea />
      {isOpen && <Menu onClose={closeModal} />}
    </>
  );
};

export default NavBar;
