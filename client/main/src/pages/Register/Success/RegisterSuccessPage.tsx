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
        <RegisterSuccessTitle>νμν΄μ! π</RegisterSuccessTitle>
      </Transition>
      <Transition delay={0.2}>
        <SuccessButtonContainer>
          <MyPageOutlineButton onClick={routeToCreatorPage}>
            π  λ΄ νμ΄μ§λ‘ λλ¬κ°κΈ°
          </MyPageOutlineButton>
          <StyledSubTitle>λλ€μ΄μμ λ°κ³  μΆλ€λ©΄?</StyledSubTitle>
          <SettlementOutlineButton onClick={routeToSettlementPage}>
            κ³μ’ λ±λ‘νκΈ°
          </SettlementOutlineButton>
          <Anchor to="/">νμΌλ‘</Anchor>
        </SuccessButtonContainer>
      </Transition>
    </StyledTemplate>
  );
};

export default RegisterSuccessPage;
