import { StyledTemplate } from './RegisterAuthPage.styles';
import RegisterAuthForm from '../../../components/Register/AuthForm/RegisterAuthForm';
import Transition from '../../../components/@atom/Transition/Transition.styles';

export const REGISTER_PAGE_KEY = 'register';

const RegisterAuthPage = () => {
  return (
    <StyledTemplate>
      <section>
        <Transition>
          <RegisterAuthForm />
        </Transition>
      </section>
    </StyledTemplate>
  );
};

export default RegisterAuthPage;
