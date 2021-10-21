import { useEffect } from 'react';
import { useHistory } from 'react-router-dom';

import Anchor from '../../../components/@atom/Anchor/Anchor';
import SubTitle from '../../../components/@atom/SubTitle/SubTitle.styles';
import Transition from '../../../components/@atom/Transition/Transition.styles';
import useRegister from '../../../service/auth/useRegister';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';
import { REGISTER_PAGE_KEY } from '../Auth/RegisterAuthPage';
import {
  MyPageOutlineButton,
  RegisterSuccessTitle,
  SuccessButtonContainer,
  StyledTemplate,
  StyledSubTitle,
  SettlementOutlineButton,
} from './RegisterSuccessPage.styles';

const RegisterSuccessPage = () => {
  const history = useHistory();
  const { user, resetRegister } = useRegister();

  const routeToCreatorPage = () => {
    history.push(`/creator/${user.pageName}`);
  };

  const routeToSettlementPage = () => {
    history.push(`/creator/${user.pageName}/settlement`);
  };

  usePageRefreshGuardEffect(REGISTER_PAGE_KEY, false, '/register');

  useEffect(() => {
    return () => {
      console.log('init');
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
          <StyledSubTitle>도네이션을 받고 싶다면?</StyledSubTitle>
          <SettlementOutlineButton onClick={routeToSettlementPage}>
            계좌 등록하기
          </SettlementOutlineButton>
          <Anchor to="/">홈으로</Anchor>
        </SuccessButtonContainer>
      </Transition>
    </StyledTemplate>
  );
};

export default RegisterSuccessPage;
