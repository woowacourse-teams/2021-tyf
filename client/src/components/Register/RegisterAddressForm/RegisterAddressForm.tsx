import { VFC } from 'react';
import Anchor from '../../@atom/Anchor/Anchor';
import Button from '../../@atom/Button/Button';
import InputWithMessage from '../../@molecule/InputWithMessage/InputWithMessage';
import { AddressInputContainer, RegisterAddressTitle } from './RegisterAddressForm.styles';

const RegisterAddressForm: VFC = () => {
  return (
    <>
      <RegisterAddressTitle>
        자신만의
        <br /> 주소를
        <br /> 적어주세요.
      </RegisterAddressTitle>
      <AddressInputContainer>
        <InputWithMessage
          isSuccess={false}
          successMessage="좋은 주소명이네요!"
          failureMessage="이미 존재하는 주소에요... 다른 주소로 입력해주세요"
        />
      </AddressInputContainer>
      <Button disabled>
        <Anchor to="/register/name">다음</Anchor>
      </Button>
    </>
  );
};

export default RegisterAddressForm;
