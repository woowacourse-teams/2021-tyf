import { useParams } from 'react-router-dom';

import { ParamTypes } from '../../App';
import LoginForm from '../../components/Login/LoginForm';
import useLoginEffect from '../../service/hooks/auth/useLoginEffect';
import { StyledTemplate } from './LoginPage.styles';

const LoginPage = () => {
  const { oauthProvider } = useParams<ParamTypes>();

  useLoginEffect(oauthProvider);

  return (
    <StyledTemplate>
      <section>
        <LoginForm />
      </section>
    </StyledTemplate>
  );
};

export default LoginPage;
