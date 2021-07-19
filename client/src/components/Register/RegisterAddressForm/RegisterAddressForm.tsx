import { useHistory } from 'react-router';

import useRegister from '../../../service/hooks/useRegister';
import useRegisterEffect from '../../../service/hooks/useRegisterEffect';
import Button from '../../@atom/Button/Button';
import InputWithMessage from '../../@molecule/InputWithMessage/InputWithMessage';
import { AddressInputContainer, RegisterAddressTitle } from './RegisterAddressForm.styles';

const RegisterAddressForm = () => {
  const history = useHistory();
  useRegisterEffect();
  const { pageName, addressErrorMessage, isValidAddress, onChangeRegister } = useRegister();

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
          name="pageName"
          role="urlName"
          value={pageName}
          isSuccess={isValidAddress}
          successMessage="좋은 주소명이네요!"
          failureMessage={addressErrorMessage}
          onChange={(e) => onChangeRegister(e.target)}
        />
      </AddressInputContainer>
      <Button disabled={!!addressErrorMessage} onClick={moveRegisterNamePage}>
        다음
      </Button>
    </>
  );
};

export default RegisterAddressForm;
