import { VFC } from 'react';
import { useHistory } from 'react-router-dom';

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
import Anchor from '../../@atom/Anchor/Anchor';

import { OAUTH } from '../../../constants/constant';

const RegisterAuthForm: VFC = () => {
  const history = useHistory();
  const openOAuthPage = (name: string) => {
    switch (name) {
      case 'goggle':
        window.open(OAUTH.GOOGLE);
        break;

      case 'naver':
        history.push('/register/url');
        break;

      case 'kakao':
        // TODO: oauth 회원가입 절차 진행
        window.open(OAUTH.KAKAO);
        break;

      default:
    }
  };

  return (
    <>
      <RegisterTitle>회원가입</RegisterTitle>
      <RegisterButtonContainer>
        <GoogleButton src={GoogleLogo} alt="google_logo" onClick={() => openOAuthPage('goggle')}>
          구글 회원가입
        </GoogleButton>
        <NaverButton src={NaverLogo} alt="naver_logo" onClick={() => openOAuthPage('naver')}>
          네이버 회원가입
        </NaverButton>
        <KakaoButton src={KakaoLogo} alt="kakao_logo" onClick={() => openOAuthPage('kakao')}>
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
