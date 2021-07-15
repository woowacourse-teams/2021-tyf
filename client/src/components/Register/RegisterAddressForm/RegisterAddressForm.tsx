import { useState, VFC } from 'react';
import { useHistory } from 'react-router';

import Button from '../../@atom/Button/Button';
import InputWithMessage from '../../@molecule/InputWithMessage/InputWithMessage';
import { AddressInputContainer, RegisterAddressTitle } from './RegisterAddressForm.styles';

const checkAddressValid = (address: string) => {
  // 최소 길이 제한 (3글자)
  if (address.length < 3) return '주소는 최소 3글자 이상, 최대 20글자 이하여야 합니다';

  return '';
};

const checkPreventAddressValid = (address: string) => {
  // 최대 길이 제한 (20글자)
  if (20 < address.length) return '주소는 최소 3글자 이상, 최대 20글자 이하여야 합니다.';

  // 공백 불가능
  if (address !== address.replace(/ /g, '')) return '주소에는 공백이 존재하면 안됩니다.';

  // 소문자만 가능
  if (address !== address.toLowerCase()) return '주소명은 소문자만 가능합니다.';

  // 영어 숫자 only, -, _ 가능\
  const regExp = /^[a-z0-9_\\-]*$/;
  if (!regExp.test(address)) return "주소는 영어 소문자, 숫자, '-', '_' 만 가능합니다.";

  return '';
};

const RegisterAddressForm: VFC = () => {
  const history = useHistory();
  const [address, setAddress] = useState('');
  const [addressErrorMessage, setAddressErrorMessage] = useState('');

  const addressMessage = checkAddressValid(address);
  const isValidAddress = !(addressMessage || addressErrorMessage);

  const onChange = ({ value }: { value: string }) => {
    const errorMessage = checkPreventAddressValid(value);

    setAddressErrorMessage(errorMessage);
    if (errorMessage) return;

    setAddress(value);
  };

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
          value={address}
          isSuccess={isValidAddress}
          successMessage="좋은 주소명이네요!"
          failureMessage={addressMessage || addressErrorMessage}
          onChange={(e) => onChange(e.target)}
        />
      </AddressInputContainer>
      <Button disabled={!!addressMessage} onClick={moveRegisterNamePage}>
        다음
      </Button>
    </>
  );
};

export default RegisterAddressForm;
