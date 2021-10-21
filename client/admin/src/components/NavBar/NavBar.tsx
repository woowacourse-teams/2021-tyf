import { useHistory } from 'react-router-dom';
import useLogin from '../../service/useLogin';
import { Menu, MenuItem, StyledNavBar, StyledTitle } from './NavBar.styles';

export const NavBar = () => {
  const history = useHistory();
  const { accessToken } = useLogin();

  return (
    <StyledNavBar>
      <StyledTitle onClick={() => history.push('/')}>ThankyouFor Admin</StyledTitle>
      <Menu>
        {accessToken && (
          <>
            <MenuItem onClick={() => history.push('/bankAccount')}>계좌신청목록</MenuItem>
            <MenuItem onClick={() => history.push('/settlement')}>정산신청목록</MenuItem>
          </>
        )}
      </Menu>
    </StyledNavBar>
  );
};
