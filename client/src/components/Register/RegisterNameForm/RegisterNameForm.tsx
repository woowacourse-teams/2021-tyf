import { VFC } from 'react';
import { useHistory } from 'react-router-dom';
import useRegister from '../../../service/hooks/useRegister';

import Button from '../../@atom/Button/Button';
import InputWithMessage from '../../@molecule/InputWithMessage/InputWithMessage';
import { NameInputContainer, RegisterNameTitle } from './RegisterNameForm.styles';

const RegisterNameForm: VFC = () => {
  const history = useHistory();
  const { nickName, nickNameErrorMessage, isValidNickName, onChangeRegister } = useRegister();

  const moveRegisterSuccessPage = () => {
    // TODO: 회원가입 절차

    history.push('/register/success');
  };

  return (
    <>
      <RegisterNameTitle>
        당신의
        <br /> 닉네임은
        <br /> 무엇인가요?
      </RegisterNameTitle>
      <NameInputContainer>
        <InputWithMessage
          name="nickName"
          value={nickName}
          isSuccess={isValidNickName}
          successMessage="좋은 닉네임이네요!"
          failureMessage={nickNameErrorMessage}
          placeholder="닉네임 입력하기"
          onChange={(e) => onChangeRegister(e.target)}
        />
      </NameInputContainer>
      <Button disabled={!!nickNameErrorMessage} onClick={moveRegisterSuccessPage}>
        회원가입 완료
      </Button>
    </>
  );
};

export default RegisterNameForm;
