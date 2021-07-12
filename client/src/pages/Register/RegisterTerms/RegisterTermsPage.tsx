import { FC, HTMLAttributes } from 'react';

import RegisterTermsForm from '../../../components/Register/RegisterTermsForm/RegisterTermsForm';
import { StyledTemplate } from './RegisterTermsPage.styles';

const RegisterTermsPage: FC<HTMLAttributes<HTMLElement>> = () => {
  return (
    <StyledTemplate>
      <RegisterTermsForm />
    </StyledTemplate>
  );
};

export default RegisterTermsPage;
