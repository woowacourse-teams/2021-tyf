import { VFC } from 'react';
import LoginForm from '../../components/Login/LoginForm';

import { StyledTemplate } from './LoginPage.styles';

const LoginPage: VFC = () => {
  return (
    <StyledTemplate>
      <LoginForm />
    </StyledTemplate>
  );
};

export default LoginPage;
