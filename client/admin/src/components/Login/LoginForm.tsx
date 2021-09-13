import { ChangeEvent } from 'react';

import useLoginForm from '../../service/useLoginForm';
import useLogin from '../../service/useLogin';
import { Button } from '../@atom/Button/Button.styles';
import { Input } from '../@atom/Input/Input.styles';
import { LoginContainer, LoginTitle, StyledLogin, StyledLoginForm } from './LoginForm.styles';

const LoginForm = () => {
  const { form, onChange, isValidForm } = useLoginForm();
  const { login } = useLogin();

  const onSubmit = (e: ChangeEvent<HTMLFormElement>) => {
    e.preventDefault();

    const { id, pwd } = form;
    login(id, pwd);
  };

  return (
    <StyledLogin>
      <StyledLoginForm onSubmit={onSubmit}>
        <LoginTitle>로그인</LoginTitle>
        <LoginContainer>
          <Input value={form.id} name="id" placeholder="아이디" onChange={onChange} />
          <Input
            type="password"
            value={form.pwd}
            name="pwd"
            placeholder="비밀번호"
            onChange={onChange}
          />
          <Button disabled={!isValidForm}>로그인</Button>
        </LoginContainer>
      </StyledLoginForm>
    </StyledLogin>
  );
};

export default LoginForm;
