import { FC, HTMLAttributes } from 'react';

import RegisterAddressForm from '../../../components/Register/RegisterAddressForm/RegisterAddressForm';
import { StyledTemplate } from './RegisterAddressPage.styles';

const RegisterAddressPage: FC<HTMLAttributes<HTMLElement>> = () => {
  return (
    <StyledTemplate>
      <RegisterAddressForm />
    </StyledTemplate>
  );
};

export default RegisterAddressPage;
