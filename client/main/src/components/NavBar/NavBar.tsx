import { useHistory } from 'react-router-dom';

import useUserInfo from '../../service//user/useUserInfo';
import { toCommaSeparatedString } from '../../utils/format';
import useModal from '../../utils/useModal';
import Menu from '../Menu/Menu';
import {
  StyledNavBar,
  StyledLogo,
  LoginButton,
  StyledTextButton,
  StyledPoint,
  NavBarArea,
  DropDownIcon,
} from './NavBar.styles';

const NavBar = () => {
  const history = useHistory();
  const { isOpen, toggleModal, closeModal } = useModal();
  const { userInfo } = useUserInfo();

  return (
    <>
      <StyledNavBar>
        <StyledLogo aria-label="logo" onClick={() => history.push('/')} />
        {userInfo ? (
          <>
            <StyledPoint onClick={() => history.push('/mypoint')}>
              {userInfo?.point && toCommaSeparatedString(userInfo.point)} tp
            </StyledPoint>
            <StyledTextButton onClick={toggleModal}>
              {userInfo.nickname} <DropDownIcon isOpen={isOpen} />
            </StyledTextButton>
          </>
        ) : (
          <>
            <LoginButton to={`/login?redirectTo=${window.location.pathname}`}>로그인</LoginButton>
            &nbsp;/&nbsp;
            <LoginButton to={`/register?redirectTo=${window.location.pathname}`}>
              회원가입
            </LoginButton>
          </>
        )}
      </StyledNavBar>
      <NavBarArea />
      {isOpen && <Menu onClose={closeModal} />}
    </>
  );
};

export default NavBar;
