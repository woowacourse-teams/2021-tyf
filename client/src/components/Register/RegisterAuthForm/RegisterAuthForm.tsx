import { VFC } from 'react';

import Anchor from '../../@atom/Anchor/Anchor';
import {
  RegisterAnchorContainer,
  RegisterButtonContainer,
  RegisterTitle,
  GoogleButton,
  KakaoButton,
  NaverButton,
} from './RegisterAuthForm.styles';
import { OAUTH } from '../../../constants/constant';
import useRegisterAuth from '../../../service/hooks/useRegisterAuth';

const RegisterAuthForm: VFC = () => {
  const { openOAuthPage } = useRegisterAuth();

  return (
    <>
      <RegisterTitle>회원가입</RegisterTitle>
      <RegisterButtonContainer>
        <GoogleButton onClick={() => openOAuthPage(OAUTH.GOOGLE.NAME)}>구글 회원가입</GoogleButton>
        <NaverButton onClick={() => openOAuthPage(OAUTH.NAVER.NAME)}>네이버 회원가입</NaverButton>
        <KakaoButton onClick={() => openOAuthPage(OAUTH.KAKAO.NAME)}>카카오 회원가입</KakaoButton>
      </RegisterButtonContainer>
      <RegisterAnchorContainer>
        <Anchor to="/login">로그인하기</Anchor>
      </RegisterAnchorContainer>
    </>
  );
};

export default RegisterAuthForm;
