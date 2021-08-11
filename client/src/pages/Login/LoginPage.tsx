import { useParams } from 'react-router-dom';

import { ParamTypes } from '../../App';
import Transition from '../../components/@atom/Transition/Transition.styles';
import LoginForm from '../../components/Login/LoginForm';
import useLoginEffect from '../../service/auth/useLoginEffect';
import { StyledTemplate } from './LoginPage.styles';

const LoginPage = () => {
  const { oauthProvider } = useParams<ParamTypes>();

  useLoginEffect(oauthProvider);

  return (
    <StyledTemplate>
      <section>
        <Transition>
          <LoginForm />
        </Transition>
      </section>
    </StyledTemplate>
  );
};

export default LoginPage;
