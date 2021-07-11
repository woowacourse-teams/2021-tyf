import { FC, HTMLAttributes } from 'react';

import Anchor from '../../../components/@atom/Anchor/Anchor';
import Template from '../../../components/@atom/Template/Template';
import {
  MyPageOutlineButton,
  RegisterSuccessContainer,
  RegisterSuccessTitle,
} from './RegisterSuccessPage.styles';

const RegisterSuccessPage: FC<HTMLAttributes<HTMLElement>> = () => {
  return (
    <Template>
      <RegisterSuccessContainer>
        <RegisterSuccessTitle>환영해요! 🎉</RegisterSuccessTitle>
        <MyPageOutlineButton>🏠 내 페이지로 놀러가기</MyPageOutlineButton>
        <Anchor>홈으로</Anchor>
      </RegisterSuccessContainer>
    </Template>
  );
};

export default RegisterSuccessPage;
