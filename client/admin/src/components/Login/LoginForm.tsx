import { ChangeEvent } from 'react';
import { useHistory } from 'react-router-dom';

import useLoginForm from '../../service/useLoginForm';
import { LoginResponse } from '../../type';
import { Button } from '../@atom/Button/Button.styles';
import { Input } from '../@atom/Input/Input.styles';
import { requestLogin } from '../request/request';
import { LoginContainer, LoginTitle, StyledLogin, StyledLoginForm } from './LoginForm.styles';

const LoginForm = () => {
  const history = useHistory();
  const { form, onChange, isValidForm } = useLoginForm();

  const onSubmit = async (e: ChangeEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const { id, pwd } = form;
      const { token }: LoginResponse = await requestLogin(id, pwd);

      sessionStorage.setItem('adminToken', token);
      history.push('/refund');
    } catch (error) {
      alert(error.message ?? error.data.message);
    }
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
