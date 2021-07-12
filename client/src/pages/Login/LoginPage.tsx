import { VFC } from 'react';
import Anchor from '../../components/@atom/Anchor/Anchor';

import GoogleLogo from '../../assets/icons/google.svg';
import NaverLogo from '../../assets/icons/naver.svg';
import KakaoLogo from '../../assets/icons/kakao.svg';
import {
  GoogleButton,
  KakaoButton,
  KeepLoginCheckbox,
  KeepLoginLabel,
  LoginAnchorContainer,
  LoginButtonContainer,
  LoginOptionContainer,
  LoginTitle,
  NaverButton,
  StyledTemplate,
} from './LoginPage.styles';

const LoginPage: VFC = () => {
  return (
    <StyledTemplate>
      <LoginTitle>로그인</LoginTitle>

      <LoginButtonContainer>
        <GoogleButton src={GoogleLogo} alt="google_logo">
          구글 로그인
        </GoogleButton>
        <NaverButton src={NaverLogo} alt="naver_logo">
          네이버 로그인
        </NaverButton>
        <KakaoButton src={KakaoLogo} alt="kakao_logo">
          카카오 로그인
        </KakaoButton>
        <LoginOptionContainer>
          <KeepLoginLabel>
            <KeepLoginCheckbox></KeepLoginCheckbox>
            로그인 유지하기
          </KeepLoginLabel>
        </LoginOptionContainer>
      </LoginButtonContainer>
      <LoginAnchorContainer>
        <Anchor to="/register">회원이 아니신가요?</Anchor>
      </LoginAnchorContainer>
    </StyledTemplate>
  );
};

export default LoginPage;
