import { StyledTemplate } from './RegisterAuthPage.styles';
import RegisterAuthForm from '../../../components/Register/AuthForm/RegisterAuthForm';

export const REGISTER_PAGE_KEY = 'register';

const RegisterAuthPage = () => {
  return (
    <StyledTemplate>
      <section>
        <RegisterAuthForm />
      </section>
    </StyledTemplate>
  );
};

export default RegisterAuthPage;
