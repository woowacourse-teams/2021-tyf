import { ChangeEvent } from 'react';

import Anchor from '../@atom/Anchor/Anchor';
import {
  KeepLoginCheckbox,
  KeepLoginLabel,
  LoginAnchorContainer,
  LoginButtonContainer,
  LoginOptionContainer,
  LoginTitle,
} from './LoginForm.styles';
import GoogleBarButton from '../@molecule/GoogleBarButton/GoogleBarButton.styles';
import NaverBarButton from '../@molecule/NaverBarButton/NaverBarButton.styles';
import KakaoBarButton from '../@molecule/KakaoBarButton/KaKaoBarButton.styles';
import { routeToOAuthPage } from '../../service/auth';
import useLoginPersistenceType from '../../service/hooks/useLoginPersistenceType';

const LoginForm = () => {
  const { loginPersistenceType, setLoginPersistenceType } = useLoginPersistenceType();

  const onChangeLoginPersistenceType = ({ target }: ChangeEvent<HTMLInputElement>) => {
    const storageType = target.checked ? 'LOCAL' : 'SESSION';

    setLoginPersistenceType(storageType);
  };

  return (
    <>
      <LoginTitle>로그인</LoginTitle>

      <LoginButtonContainer>
        <GoogleBarButton onClick={() => routeToOAuthPage('GOOGLE', 'LOGIN')}>
          구글 로그인
        </GoogleBarButton>
        <NaverBarButton onClick={() => routeToOAuthPage('NAVER', 'LOGIN')}>
          네이버 로그인
        </NaverBarButton>
        <KakaoBarButton onClick={() => routeToOAuthPage('KAKAO', 'LOGIN')}>
          카카오 로그인
        </KakaoBarButton>
        <LoginOptionContainer>
          <KeepLoginLabel>
            <KeepLoginCheckbox
              checked={loginPersistenceType === 'LOCAL'}
              onChange={onChangeLoginPersistenceType}
            ></KeepLoginCheckbox>
            로그인 유지하기
          </KeepLoginLabel>
        </LoginOptionContainer>
      </LoginButtonContainer>
      <LoginAnchorContainer>
        <Anchor to="/register">회원이 아니신가요?</Anchor>
      </LoginAnchorContainer>
    </>
  );
};

export default LoginForm;
