import { StyledTemplate } from './RegisterAuthPage.styles';
import RegisterAuthForm from '../../../components/Register/AuthForm/RegisterAuthForm';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';

export const REGISTER_PAGE_KEY = 'register';

const RegisterAuthPage = () => {
  usePageRefreshGuardEffect(REGISTER_PAGE_KEY, true, '/register');

  return (
    <StyledTemplate>
      <section>
        <RegisterAuthForm />
      </section>
    </StyledTemplate>
  );
};

export default RegisterAuthPage;
