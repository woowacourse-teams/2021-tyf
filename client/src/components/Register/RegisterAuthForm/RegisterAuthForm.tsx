import React, { VFC } from 'react';

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
import { useHistory } from 'react-router';

const RegisterAuthForm: VFC = () => {
  const openOAuthPage = (name: string) => {
    switch (name) {
      case 'goggle':
        window.open(
          'https://accounts.google.com/o/oauth2/v2/auth?client_id=153785509866-72pebue5t5qqcpci2d1bncrh497ootcc.apps.googleusercontent.com&redirect_uri=http://localhost:9000/register/url&response_type=code&scope=https://www.googleapis.com/auth/userinfo.profile email&access_type=offline',
          '_self'
        );

        break;

      case 'naver':
        alert('서비스 준비중입니다');

        break;

      case 'kakao':
        // TODO: oauth 회원가입 절차 진행
        window.open(
          'https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=d187d243372db29c85e85eef81d4723a&redirect_uri=http://localhost:9000/register/url',
          '_self'
        );

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
