import RegisterTermsForm from '../../../components/Register/TermsForm/RegisterTermsForm';
import usePageRefreshGuardEffect from '../../../utils/usePageRefreshGuardEffect';
import { REGISTER_PAGE_KEY } from '../Auth/RegisterAuthPage';
import { StyledTemplate } from './RegisterTermsPage.styles';

const RegisterTermsPage = () => {
  usePageRefreshGuardEffect(REGISTER_PAGE_KEY, true, '/register');

  return (
    <StyledTemplate>
      <RegisterTermsForm />
    </StyledTemplate>
  );
};

export default RegisterTermsPage;
