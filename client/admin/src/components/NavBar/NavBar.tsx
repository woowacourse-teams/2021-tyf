import { useHistory } from 'react-router-dom';
import { Menu, MenuItem, StyledNavBar, StyledTitle } from './NavBar.styles';

export const NavBar = () => {
  const history = useHistory();

  return (
    <StyledNavBar>
      <StyledTitle>ThankyouFor Admin</StyledTitle>
      <Menu>
        <MenuItem onClick={() => history.push('/bankAccount')}>계좌신청목록</MenuItem>
        <MenuItem onClick={() => history.push('/settlement')}>정산신청목록</MenuItem>
      </Menu>
    </StyledNavBar>
  );
};
