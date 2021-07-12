import Anchor from '../@atom/Anchor/Anchor';
import { StyledModal, ProfileContainer, URLCopyButton } from './Menu.styles';

const Menu = () => {
  const isLogin = true;

  return (
    <StyledModal>
      <ProfileContainer>
        {isLogin ? (
          <>
            <span>파노</span>
            <URLCopyButton>후원URL 복사</URLCopyButton>
          </>
        ) : (
          <Anchor>로그인을 해주세요 {'>'}</Anchor>
        )}
      </ProfileContainer>
      {isLogin && (
        <>
          <Anchor>마이페이지</Anchor>
          <Anchor>후원 통계</Anchor>
          <Anchor>로그아웃</Anchor>
        </>
      )}
    </StyledModal>
  );
};

export default Menu;
