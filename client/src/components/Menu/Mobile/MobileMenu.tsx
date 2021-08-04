import useLogout from '../../../service/hooks/auth/useLogout';
import useUserInfo from '../../../service/hooks/user/useUserInfo';
import { donationUrlShare } from '../../../service/share';
import { ProfileContainer, StyledModal, StyledAnchor, URLCopyButton } from './MobileMenu.styles';

export interface MobileMenuProps {
  onClose: () => void;
}

const MobileMenu = ({ onClose }: MobileMenuProps) => {
  const { userInfo } = useUserInfo();
  const { logout } = useLogout();

  const shareURL = () => {
    if (!userInfo) return;

    donationUrlShare(userInfo.nickname, userInfo.pageName);
  };

  const onLogout = () => {
    onClose();
    logout();
  };

  return (
    <StyledModal onClose={onClose}>
      <ProfileContainer>
        <StyledAnchor to={`/creator/${userInfo?.pageName}`} onClick={onClose}>
          {userInfo?.nickname}
        </StyledAnchor>
        <URLCopyButton onClick={shareURL}>후원 URL 복사</URLCopyButton>
      </ProfileContainer>
      <StyledAnchor to={`/creator/${userInfo?.pageName}`} onClick={onClose}>
        마이페이지
      </StyledAnchor>
      <StyledAnchor to={`/creator/${userInfo?.pageName}/statistic`} onClick={onClose}>
        후원 통계
      </StyledAnchor>
      <StyledAnchor to={`/creator/${userInfo?.pageName}/setting`} onClick={onClose}>
        설정
      </StyledAnchor>
      <StyledAnchor to="/" onClick={onLogout}>
        로그아웃
      </StyledAnchor>
    </StyledModal>
  );
};

export default MobileMenu;
