import { FormEvent } from 'react';
import useRegister from '../../../service/hooks/useRegister';
import Button from '../../@atom/Button/Button';
import ValidationInput from '../../@molecule/ValidationInput/ValidationInput';
import {
  StyledRegisterNameForm,
  NameInputContainer,
  RegisterNameTitle,
} from './RegisterNameForm.styles';

const RegisterNameForm = () => {
  const { nickname, nickNameErrorMessage, isValidNickName, setNickname, registerUser } =
    useRegister();

  const onRegister = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    await registerUser();
  };

  return (
    <StyledRegisterNameForm onSubmit={onRegister}>
      <RegisterNameTitle>
        당신의
        <br /> 닉네임은
        <br /> 무엇인가요?
      </RegisterNameTitle>
      <NameInputContainer>
        <ValidationInput
          role="nickname"
          value={nickname}
          onChange={({ target }) => setNickname(target.value)}
          isSuccess={isValidNickName}
          successMessage="좋은 닉네임이네요!"
          failureMessage={nickNameErrorMessage}
          placeholder="닉네임 입력하기"
        />
      </NameInputContainer>
      <Button disabled={!!nickNameErrorMessage}>회원가입 완료</Button>
    </StyledRegisterNameForm>
  );
};

export default RegisterNameForm;
