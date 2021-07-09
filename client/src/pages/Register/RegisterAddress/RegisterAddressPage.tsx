import { FC, HTMLAttributes } from 'react';
import Button from '../../../components/@atom/Button/Button';

import Template from '../../../components/@atom/Template/Template';
import InputWithMessage from '../../../components/@molecule/InputWithMessage/InputWithMessage';
import {
  AddressInputContainer,
  RegisterAddressContainer,
  RegisterAddressTitle,
} from './RegisterAddressPage.styles';

const RegisterAddressPage: FC<HTMLAttributes<HTMLElement>> = () => {
  return (
    <Template>
      <RegisterAddressContainer>
        <RegisterAddressTitle>자신만의 주소를 적어주세요.</RegisterAddressTitle>
        <AddressInputContainer>
          <InputWithMessage
            isSuccess={false}
            successMessage="좋은 주소명이네요!"
            failureMessage="이미 존재하는 주소에요... 다른 주소로 입력해주세요"
          />
        </AddressInputContainer>
        <Button disabled>다음</Button>
      </RegisterAddressContainer>
    </Template>
  );
};

export default RegisterAddressPage;
