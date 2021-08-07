import { useEffect } from 'react';
import { useHistory } from 'react-router-dom';

import Anchor from '../../../components/@atom/Anchor/Anchor';
import Transition from '../../../components/@atom/Transition/Transition.styles';
import useRegister from '../../../service/hooks/auth/useRegister';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';
import { REGISTER_PAGE_KEY } from '../Auth/RegisterAuthPage';
import {
  MyPageOutlineButton,
  RegisterSuccessTitle,
  SuccessButtonContainer,
  StyledTemplate,
} from './RegisterSuccessPage.styles';

const RegisterSuccessPage = () => {
  const history = useHistory();
  const { user, resetRegister } = useRegister();

  const routeToCreatorPage = () => {
    history.push(`/creator/${user.pageName}`);
  };

  usePageRefreshGuardEffect(REGISTER_PAGE_KEY, false, '/register');

  useEffect(() => {
    return () => {
      resetRegister();
    };
  }, []);

  return (
    <StyledTemplate>
      <Transition>
        <RegisterSuccessTitle>환영해요! 🎉</RegisterSuccessTitle>
      </Transition>
      <Transition delay={0.2}>
        <SuccessButtonContainer>
          <MyPageOutlineButton onClick={routeToCreatorPage}>
            🏠 내 페이지로 놀러가기
          </MyPageOutlineButton>
          <Anchor to="/">홈으로</Anchor>
        </SuccessButtonContainer>
      </Transition>
    </StyledTemplate>
  );
};

export default RegisterSuccessPage;
