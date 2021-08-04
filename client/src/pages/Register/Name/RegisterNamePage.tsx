import RegisterNameForm from '../../../components/Register/NameForm/RegisterNameForm';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';
import { REGISTER_PAGE_KEY } from '../Auth/RegisterAuthPage';
import { StyledTemplate } from './RegisterNamePage.styles';

const RegisterNamePage = () => {
  usePageRefreshGuardEffect(REGISTER_PAGE_KEY, false, '/register');

  return (
    <StyledTemplate>
      <RegisterNameForm />
    </StyledTemplate>
  );
};

export default RegisterNamePage;
