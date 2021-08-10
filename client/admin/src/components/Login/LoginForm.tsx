import { Button } from '../@atom/Button/Button.styles';
import { Input } from '../@atom/Input/Input.styles';
import { LoginContainer, LoginTitle, StyledLoginForm } from './LoginForm.styles';

const LoginForm = () => {
  return (
    <StyledLoginForm>
      <LoginTitle>로그인</LoginTitle>
      <LoginContainer>
        <Input placeholder="아이디" />
        <Input placeholder="비밀번호" />
        <Button>로그인</Button>
      </LoginContainer>
    </StyledLoginForm>
  );
};

export default LoginForm;
