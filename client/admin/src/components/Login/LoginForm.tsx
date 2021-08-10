import { ChangeEvent, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { Button } from '../@atom/Button/Button.styles';
import { Input } from '../@atom/Input/Input.styles';
import { LoginContainer, LoginTitle, StyledLoginForm } from './LoginForm.styles';

const LoginForm = () => {
  const history = useHistory();
  const [form, setForm] = useState({
    id: '',
    pwd: '',
  });

  const onChange = ({ target }: ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [target.name]: target.value });
  };

  const onSubmit = (e: ChangeEvent<HTMLFormElement>) => {
    // request
    history.push('/refund');
  };

  return (
    <StyledLoginForm onSubmit={onSubmit}>
      <LoginTitle>로그인</LoginTitle>
      <LoginContainer>
        <Input value={form.id} name="id" placeholder="아이디" onChange={onChange} />
        <Input value={form.pwd} name="pwd" placeholder="비밀번호" onChange={onChange} />
        <Button>로그인</Button>
      </LoginContainer>
    </StyledLoginForm>
  );
};

export default LoginForm;
