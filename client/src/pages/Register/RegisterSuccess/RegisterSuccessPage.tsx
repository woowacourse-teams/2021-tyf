import { FC, HTMLAttributes, useEffect } from 'react';
import { useHistory } from 'react-router-dom';

import Anchor from '../../../components/@atom/Anchor/Anchor';
import useRegister from '../../../service/hooks/useRegister';
import {
  MyPageOutlineButton,
  RegisterSuccessTitle,
  SuccessButtonContainer,
  StyledTemplate,
} from './RegisterSuccessPage.styles';

const RegisterSuccessPage: FC<HTMLAttributes<HTMLElement>> = () => {
  const history = useHistory();
  const { urlName, onResetRegister } = useRegister();

  const moveCreatorPage = () => {
    history.push(`/creator/${urlName}`);
  };

  useEffect(() => {
    return () => {
      onResetRegister();
    };
  }, []);

  return (
    <StyledTemplate>
      <RegisterSuccessTitle>환영해요! 🎉</RegisterSuccessTitle>
      <SuccessButtonContainer>
        <MyPageOutlineButton onClick={moveCreatorPage}>🏠 내 페이지로 놀러가기</MyPageOutlineButton>
        <Anchor to="/">홈으로</Anchor>
      </SuccessButtonContainer>
    </StyledTemplate>
  );
};

export default RegisterSuccessPage;
