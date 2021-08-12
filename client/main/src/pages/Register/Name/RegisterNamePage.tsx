import Transition from '../../../components/@atom/Transition/Transition.styles';
import RegisterNameForm from '../../../components/Register/NameForm/RegisterNameForm';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';
import { REGISTER_PAGE_KEY } from '../Auth/RegisterAuthPage';
import { StyledTemplate } from './RegisterNamePage.styles';

const RegisterNamePage = () => {
  usePageRefreshGuardEffect(REGISTER_PAGE_KEY, false, '/register');

  return (
    <StyledTemplate>
      <Transition>
        <RegisterNameForm />
      </Transition>
    </StyledTemplate>
  );
};

export default RegisterNamePage;
