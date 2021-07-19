import { VFC } from 'react';

import RegisterNameForm from '../../../components/Register/RegisterNameForm/RegisterNameForm';
import { StyledTemplate } from './RegisterNamePage.styles';

const RegisterNamePage: VFC = () => {
  return (
    <StyledTemplate>
      <RegisterNameForm />
    </StyledTemplate>
  );
};

export default RegisterNamePage;
