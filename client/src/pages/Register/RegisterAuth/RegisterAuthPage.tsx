import { VFC, HTMLAttributes } from 'react';

import Anchor from '../../../components/@atom/Anchor/Anchor';
import GoogleLogo from '../../../assets/icons/google.svg';
import NaverLogo from '../../../assets/icons/naver.svg';
import KakaoLogo from '../../../assets/icons/kakao.svg';
import {
  RegisterAnchorContainer,
  RegisterButtonContainer,
  RegisterTitle,
  GoogleButton,
  KakaoButton,
  NaverButton,
  StyledTemplate,
} from './RegisterAuthPage.styles';

const RegisterAuthPage: VFC = () => {
  return (
    <StyledTemplate>
      <RegisterTitle>회원가입</RegisterTitle>

      <RegisterButtonContainer>
        <GoogleButton src={GoogleLogo} alt="google_logo">
          구글 회원가입
        </GoogleButton>
        <NaverButton src={NaverLogo} alt="naver_logo">
          네이버 회원가입
        </NaverButton>
        <KakaoButton src={KakaoLogo} alt="kakao_logo">
          카카오 회원가입
        </KakaoButton>
      </RegisterButtonContainer>
      <RegisterAnchorContainer>
        <Anchor to="/register/url">로그인하기</Anchor>
      </RegisterAnchorContainer>
    </StyledTemplate>
  );
};

export default RegisterAuthPage;
