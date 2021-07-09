import { FC, HTMLAttributes } from 'react';
import Button from '../../../components/@atom/Button/Button';
import Template from '../../../components/@atom/Template/Template';
import InputWithMessage from '../../../components/@molecule/InputWithMessage/InputWithMessage';

import {
  NameInputContainer,
  RegisterNameContainer,
  RegisterNameTitle,
} from './RegisterNamePage.styles';

const RegisterNamePage: FC<HTMLAttributes<HTMLElement>> = () => {
  return (
    <Template>
      <RegisterNameContainer>
        <RegisterNameTitle>당신의 닉네임은 무엇인가요?</RegisterNameTitle>
        <NameInputContainer>
          <InputWithMessage
            isSuccess={false}
            successMessage="좋은 닉네임이네요!"
            failureMessage="이미 존재하는 닉네임이에요... 다른 닉네임으로 시도해주세요!"
            placeholder="닉네임 입력하기"
          />
        </NameInputContainer>
        <Button disabled>회원가입 완료</Button>
      </RegisterNameContainer>
    </Template>
  );
};

export default RegisterNamePage;
