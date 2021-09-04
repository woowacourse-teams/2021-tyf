import { useHistory } from 'react-router-dom';

import useUserInfo from '../../service//user/useUserInfo';
import useModal from '../../utils/useModal';
import Menu from '../Menu/Menu';
import {
  StyledNavBar,
  StyledLogo,
  LoginButton,
  StyledTextButton,
  StyledPoint,
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
          <>
            <StyledPoint onClick={() => history.push('/point')}>21,000 tp</StyledPoint>
            <StyledTextButton onClick={toggleModal}>{userInfo.nickname}</StyledTextButton>
          </>
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
