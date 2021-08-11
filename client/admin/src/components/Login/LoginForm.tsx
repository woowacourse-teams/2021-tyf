import { ChangeEvent, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { isStyledComponent } from 'styled-components';
import { BASE_URL } from '../../constants/api';
import { Button } from '../@atom/Button/Button.styles';
import { Input } from '../@atom/Input/Input.styles';
import { requestLogin } from '../request/request';
import { LoginContainer, LoginTitle, StyledLogin, StyledLoginForm } from './LoginForm.styles';

interface LoginResponse {
  data?: {
    token: string;
  };
  errors?: Array<{ message: string }>;
}

const LoginForm = () => {
  const history = useHistory();
  const [form, setForm] = useState({
    id: '',
    pwd: '',
  });

  const onChange = ({ target }: ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [target.name]: target.value });
  };

  const onSubmit = async (e: ChangeEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const { id, pwd } = form;
      const { data }: LoginResponse = await requestLogin(id, pwd);

      sessionStorage.setItem('adminToken', data!.token);
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
          <Button>로그인</Button>
        </LoginContainer>
      </StyledLoginForm>
    </StyledLogin>
  );
};

export default LoginForm;
