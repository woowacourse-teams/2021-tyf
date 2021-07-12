import { FC, HTMLAttributes } from 'react';

import Anchor from '../../../components/@atom/Anchor/Anchor';
import {
  MyPageOutlineButton,
  RegisterSuccessTitle,
  SuccessButtonContainer,
  StyledTemplate,
} from './RegisterSuccessPage.styles';

const RegisterSuccessPage: FC<HTMLAttributes<HTMLElement>> = () => {
  return (
    <StyledTemplate>
      <RegisterSuccessTitle>환영해요! 🎉</RegisterSuccessTitle>
      <SuccessButtonContainer>
        <MyPageOutlineButton>🏠 내 페이지로 놀러가기</MyPageOutlineButton>
        <Anchor to="/">홈으로</Anchor>
      </SuccessButtonContainer>
    </StyledTemplate>
  );
};

export default RegisterSuccessPage;
