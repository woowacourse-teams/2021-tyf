import { FC, HTMLAttributes } from 'react';
import Anchor from '../../components/@atom/Anchor/Anchor';
import Template from '../../components/@atom/Template/Template';

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
  LoginContainer,
  LoginOptionContainer,
  LoginTitle,
  NaverButton,
} from './LoginPage.styles';

const LoginPage: FC<HTMLAttributes<HTMLElement>> = () => {
  return (
    <Template>
      <LoginContainer>
        <LoginTitle>로그인</LoginTitle>

        <LoginButtonContainer>
          <GoogleButton logoSrc={GoogleLogo} logoAlt="google_logo">
            구글 로그인
          </GoogleButton>
          <NaverButton logoSrc={NaverLogo} logoAlt="naver_logo">
            네이버 로그인
          </NaverButton>
          <KakaoButton logoSrc={KakaoLogo} logoAlt="kakao_logo">
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
          <Anchor href="">회원이 아니신가요?</Anchor>
        </LoginAnchorContainer>
      </LoginContainer>
    </Template>
  );
};

export default LoginPage;
