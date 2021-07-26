import { StyledDesktopMenu, MenuButton, MenuIcon } from './DesktopMenu.styles';

import Home from '../../../assets/icons/home.svg';
import Graph from '../../../assets/icons/graph.svg';
import User from '../../../assets/icons/user.svg';
import Setting from '../../../assets/icons/setting.svg';
import Logout from '../../../assets/icons/logout.svg';
import { useHistory } from 'react-router-dom';
import useUserInfo from '../../../service/hooks/useUserInfo';
import useLogout from '../../../service/hooks/useLogout';

export interface DesktopMenuProps {
  onClose: () => void;
}

const DesktopMenu = ({ onClose }: DesktopMenuProps) => {
  const history = useHistory();
  const { userInfo } = useUserInfo();
  const { logout } = useLogout();

  const onLogout = () => {
    onClose();
    logout();
  };

  const routeTo = (path: string) => {
    console.log(path);
    history.push(path);
    onClose();
  };

  return (
    <StyledDesktopMenu>
      <MenuButton onClick={() => routeTo('/')}>
        <MenuIcon src={Home} />
        <span>홈</span>
      </MenuButton>
      <MenuButton onClick={() => routeTo(`/creator/${userInfo?.pageName}`)}>
        <MenuIcon src={User} />
        <span>마이페이지</span>
      </MenuButton>
      <MenuButton onClick={() => routeTo(`/creator/${userInfo?.pageName}/statistic`)}>
        <MenuIcon src={Graph} />
        <span>통계</span>
      </MenuButton>
      <MenuButton onClick={() => routeTo(`/creator/${userInfo?.pageName}/setting`)}>
        <MenuIcon src={Setting} />
        <span>설정</span>
      </MenuButton>
      <MenuButton onClick={onLogout}>
        <MenuIcon src={Logout} />
        <span>로그아웃</span>
      </MenuButton>
    </StyledDesktopMenu>
  );
};

export default DesktopMenu;
