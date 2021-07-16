import { VFC } from 'react';
import { useHistory } from 'react-router';
import useRegister from '../../../service/hooks/useRegister';

import Button from '../../@atom/Button/Button';
import InputWithMessage from '../../@molecule/InputWithMessage/InputWithMessage';
import { AddressInputContainer, RegisterAddressTitle } from './RegisterAddressForm.styles';

const RegisterAddressForm: VFC = () => {
  const history = useHistory();
  const { urlName, addressErrorMessage, isValidAddress, onChangeAddress } = useRegister();

  const moveRegisterNamePage = () => {
    history.push('/register/name');
  };

  return (
    <>
      <RegisterAddressTitle>
        자신만의
        <br /> 주소를
        <br /> 적어주세요.
      </RegisterAddressTitle>
      <AddressInputContainer>
        <InputWithMessage
          value={urlName}
          isSuccess={isValidAddress}
          successMessage="좋은 주소명이네요!"
          failureMessage={addressErrorMessage}
          onChange={(e) => onChangeAddress(e.target)}
        />
      </AddressInputContainer>
      <Button disabled={!!addressErrorMessage} onClick={moveRegisterNamePage}>
        다음
      </Button>
    </>
  );
};

export default RegisterAddressForm;
