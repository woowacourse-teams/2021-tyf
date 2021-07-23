import { FormEvent } from 'react';
import { useHistory } from 'react-router';

import useRegisterPageName from '../../../service/hooks/useRegisterPageName';
import Button from '../../@atom/Button/Button';
import ValidationInput from '../../@molecule/ValidationInput/ValidationInput';
import {
  StyledRegisterAddressForm,
  AddressInputContainer,
  RegisterAddressTitle,
} from './RegisterAddressForm.styles';

const RegisterAddressForm = () => {
  const history = useHistory();
  const { pageName, addressErrorMessage, isValidAddress, setPageName } = useRegisterPageName();

  const routeToRegisterNamePage = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    history.push('/register/name');
  };

  return (
    <StyledRegisterAddressForm onSubmit={routeToRegisterNamePage}>
      <RegisterAddressTitle>
        도네이션
        <br /> 받을 주소를
        <br /> 적어주세요.
      </RegisterAddressTitle>
      <AddressInputContainer>
        <ValidationInput
          role="url-name"
          value={pageName}
          onChange={({ target }) => setPageName(target.value)}
          isSuccess={isValidAddress}
          successMessage="좋은 주소명이네요!"
          failureMessage={addressErrorMessage}
        />
      </AddressInputContainer>
      <Button disabled={!!addressErrorMessage}>다음</Button>
    </StyledRegisterAddressForm>
  );
};

export default RegisterAddressForm;
