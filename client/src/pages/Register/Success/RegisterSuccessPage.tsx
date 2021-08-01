import { useEffect } from 'react';
import { useHistory } from 'react-router-dom';

import Anchor from '../../../components/@atom/Anchor/Anchor';
import useRegister from '../../../service/hooks/useRegister';
import {
  MyPageOutlineButton,
  RegisterSuccessTitle,
  SuccessButtonContainer,
  StyledTemplate,
} from './RegisterSuccessPage.styles';

const RegisterSuccessPage = () => {
  const history = useHistory();
  const { pageName, resetRegister } = useRegister();

  const routeToCreatorPage = () => {
    history.push(`/creator/${pageName}`);
  };

  useEffect(() => {
    return () => {
      resetRegister();
    };
  }, []);

  return (
    <StyledTemplate>
      <RegisterSuccessTitle>환영해요! 🎉</RegisterSuccessTitle>
      <SuccessButtonContainer>
        <MyPageOutlineButton onClick={routeToCreatorPage}>
          🏠 내 페이지로 놀러가기
        </MyPageOutlineButton>
        <Anchor to="/">홈으로</Anchor>
      </SuccessButtonContainer>
    </StyledTemplate>
  );
};

export default RegisterSuccessPage;
