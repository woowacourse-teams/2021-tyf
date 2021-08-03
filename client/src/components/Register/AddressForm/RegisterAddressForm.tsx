import { FormEvent } from 'react';
import { useHistory } from 'react-router';

import useRegisterPageName from '../../../service/hooks/useRegisterPageName';
import ValidationInput from '../../@molecule/ValidationInput/ValidationInput';
import {
  StyledRegisterAddressForm,
  AddressInputContainer,
  RegisterAddressTitle,
  StyledButton,
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
        <span>도네이션 </span>
        <span>받을 주소를 </span>
        <span>적어주세요 </span>
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
      <StyledButton disabled={!isValidAddress}>다음</StyledButton>
    </StyledRegisterAddressForm>
  );
};

export default RegisterAddressForm;
