import { FormEvent } from 'react';

import useRegister from '../../../service/auth/useRegister';
import useRegisterNickname from '../../../service/auth/useRegisterNickname';
import ValidationInput from '../../@molecule/ValidationInput/ValidationInput';
import {
  StyledRegisterNameForm,
  NameInputContainer,
  RegisterNameTitle,
  StyledButton,
} from './RegisterNameForm.styles';

const RegisterNameForm = () => {
  const { nickname, nicknameErrorMessage, isValidNickName, setNickname } = useRegisterNickname();
  const { registerUser } = useRegister();

  const onRegister = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    await registerUser();
  };

  return (
    <StyledRegisterNameForm onSubmit={onRegister}>
      <RegisterNameTitle>
        <span>당신의 </span>
        <span>닉네임은 </span>
        <span>무엇인가요?</span>
      </RegisterNameTitle>
      <NameInputContainer>
        <ValidationInput
          role="nickname"
          value={nickname}
          onChange={({ target }) => setNickname(target.value)}
          isSuccess={isValidNickName}
          successMessage="좋은 닉네임이네요!"
          failureMessage={nicknameErrorMessage}
          placeholder="닉네임 입력하기"
        />
      </NameInputContainer>
      <StyledButton disabled={!isValidNickName}>회원가입 완료</StyledButton>
    </StyledRegisterNameForm>
  );
};

export default RegisterNameForm;
