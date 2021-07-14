import { VFC } from 'react';
import { useHistory } from 'react-router-dom';

import Anchor from '../../@atom/Anchor/Anchor';
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
} from './RegisterAuthForm.styles';
import { OAUTH } from '../../../constants/constant';

const RegisterAuthForm: VFC = () => {
  const history = useHistory();

  const openOAuthPage = (name: string) => {
    switch (name) {
      case OAUTH.GOOGLE.NAME:
        window.open(OAUTH.GOOGLE.URL, '_target');
        break;

      case OAUTH.NAVER.NAME:
        // (다음 페이지로 넘어가기 위한 임시 코드로 대체)
        // window.open(OAUTH.NAVER, '_target');
        history.push('/register/url');
        break;

      case OAUTH.KAKAO.NAME:
        window.open(OAUTH.KAKAO.URL, '_target');
        break;

      default:
    }
  };

  return (
    <>
      <RegisterTitle>회원가입</RegisterTitle>
      <RegisterButtonContainer>
        <GoogleButton
          src={GoogleLogo}
          alt="google_logo"
          onClick={() => openOAuthPage(OAUTH.GOOGLE.NAME)}
        >
          구글 회원가입
        </GoogleButton>
        <NaverButton
          src={NaverLogo}
          alt="naver_logo"
          onClick={() => openOAuthPage(OAUTH.NAVER.NAME)}
        >
          네이버 회원가입
        </NaverButton>
        <KakaoButton
          src={KakaoLogo}
          alt="kakao_logo"
          onClick={() => openOAuthPage(OAUTH.KAKAO.NAME)}
        >
          카카오 회원가입
        </KakaoButton>
      </RegisterButtonContainer>
      <RegisterAnchorContainer>
        <Anchor to="/login">로그인하기</Anchor>
      </RegisterAnchorContainer>
    </>
  );
};

export default RegisterAuthForm;
