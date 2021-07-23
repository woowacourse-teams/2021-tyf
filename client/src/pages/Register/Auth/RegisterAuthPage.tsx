import { StyledTemplate } from './RegisterAuthPage.styles';
import RegisterAuthForm from '../../../components/Register/AuthForm/RegisterAuthForm';

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
