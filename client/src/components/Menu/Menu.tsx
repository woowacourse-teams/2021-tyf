import { VFC } from 'react';

import useLoginUserInfo from '../../service/hooks/useLoginUserInfo';
import Anchor from '../@atom/Anchor/Anchor';
import { StyledModal, ProfileContainer, URLCopyButton, StyledAnchor } from './Menu.styles';
import useLogout from '../../service/hooks/useLogout';
import { donationUrlShare } from '../../service/share';

export interface MenuProps {
  onClose: () => void;
}

const Menu: VFC<MenuProps> = ({ onClose }) => {
  const { userInfo } = useLoginUserInfo();
  const { logout } = useLogout();

  const onLogout = () => {
    logout();
    onClose();
  };

  const shareURL = () => {
    if (!userInfo) return;

    donationUrlShare(userInfo.nickname, userInfo.pageName);
  };

  return (
    <StyledModal onClose={onClose}>
      <ProfileContainer>
        {userInfo ? (
          <>
            <span>{userInfo.nickname}</span>
            <URLCopyButton onClick={shareURL}>후원URL 복사</URLCopyButton>
          </>
        ) : (
          <StyledAnchor to="/login" onClick={onClose}>
            로그인을 해주세요&nbsp;&nbsp; {'>'}
          </StyledAnchor>
        )}
      </ProfileContainer>
      {userInfo && (
        <>
          <StyledAnchor to={`/creator/${userInfo?.pageName}`} onClick={onClose}>
            마이페이지
          </StyledAnchor>
          <StyledAnchor to={`/creator/${userInfo?.pageName}/statistic`} onClick={onClose}>
            후원 통계
          </StyledAnchor>
          <StyledAnchor to="/" onClick={onLogout}>
            로그아웃
          </StyledAnchor>
        </>
      )}
    </StyledModal>
  );
};

export default Menu;
