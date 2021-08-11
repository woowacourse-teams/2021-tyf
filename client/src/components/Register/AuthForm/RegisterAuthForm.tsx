import Anchor from '../../@atom/Anchor/Anchor';
import GoogleBarButton from '../../@molecule/GoogleBarButton/GoogleBarButton.styles';
import NaverBarButton from '../../@molecule/NaverBarButton/NaverBarButton.styles';
import KakaoBarButton from '../../@molecule/KakaoBarButton/KaKaoBarButton.styles';
import { routeToOAuthPage } from '../../../service/auth/routeToOauthPage';
import {
  RegisterAnchorContainer,
  RegisterButtonContainer,
  RegisterTitle,
} from './RegisterAuthForm.styles';

const RegisterAuthForm = () => {
  return (
    <>
      <RegisterTitle>회원가입</RegisterTitle>
      <RegisterButtonContainer>
        <GoogleBarButton onClick={() => routeToOAuthPage('GOOGLE', 'REGISTER')}>
          구글 회원가입
        </GoogleBarButton>
        <NaverBarButton onClick={() => routeToOAuthPage('NAVER', 'REGISTER')}>
          네이버 회원가입
        </NaverBarButton>
        <KakaoBarButton onClick={() => routeToOAuthPage('KAKAO', 'REGISTER')}>
          카카오 회원가입
        </KakaoBarButton>
      </RegisterButtonContainer>
      <RegisterAnchorContainer>
        <Anchor to="/login">로그인하기</Anchor>
      </RegisterAnchorContainer>
    </>
  );
};

export default RegisterAuthForm;
