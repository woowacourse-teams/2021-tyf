import { VFC } from 'react';
import { useParams } from 'react-router-dom';
import { ParamTypes } from '../../App';

import LoginForm from '../../components/Login/LoginForm';
import useLoginEffect from '../../service/hooks/useLoginEffect';
import { StyledTemplate } from './LoginPage.styles';

const LoginPage: VFC = () => {
  const { oauthProvider } = useParams<ParamTypes>();

  useLoginEffect(oauthProvider);

  return (
    <StyledTemplate>
      <LoginForm />
    </StyledTemplate>
  );
};

export default LoginPage;
