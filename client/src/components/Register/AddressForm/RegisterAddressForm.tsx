import { useHistory } from 'react-router';

import useRegister from '../../../service/hooks/useRegister';
import useRegisterEffect from '../../../service/hooks/useRegisterEffect';
import Button from '../../@atom/Button/Button';
import ValidationInput from '../../@molecule/ValidationInput/ValidationInput';
import { AddressInputContainer, RegisterAddressTitle } from './RegisterAddressForm.styles';

const RegisterAddressForm = () => {
  const history = useHistory();
  const { pageName, addressErrorMessage, isValidAddress, setPageName } = useRegister();

  const routeToRegisterNamePage = () => {
    history.push('/register/name');
  };

  useRegisterEffect();

  return (
    <>
      <RegisterAddressTitle>
        자신만의
        <br /> 주소를
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
      <Button disabled={!!addressErrorMessage} onClick={routeToRegisterNamePage}>
        다음
      </Button>
    </>
  );
};

export default RegisterAddressForm;
