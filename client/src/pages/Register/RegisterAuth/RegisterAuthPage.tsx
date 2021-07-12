import { VFC } from 'react';

import { StyledTemplate } from './RegisterAuthPage.styles';
import RegisterAuthForm from '../../../components/Register/RegisterAuthForm/RegisterAuthForm';

const RegisterAuthPage: VFC = () => {
  return (
    <StyledTemplate>
      <RegisterAuthForm />
    </StyledTemplate>
  );
};

export default RegisterAuthPage;
