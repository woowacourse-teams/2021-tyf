import { useRecoilState, useRecoilValue } from 'recoil';

import {
  newUserState,
  nickNameValidationSelector,
  urlNameValidationSelector,
} from '../state/register';
import { requestRegister } from '../request/register';
import { useHistory } from 'react-router-dom';

const useRegister = () => {
  const history = useHistory();
  const [user, setUser] = useRecoilState(newUserState);
  const addressErrorMessage = useRecoilValue(urlNameValidationSelector);
  const nickNameErrorMessage = useRecoilValue(nickNameValidationSelector);

  // TODO: db로의 검증
  // const addressDBErrorMessage = useRecoilValueLoadable(urlNameDBValidationQuery);
  // const nickNameDBErrorMessage = useRecoilValueLoadable(nickNameDBValidationQuery);

  const { pageName, nickname } = user;

  const isValidAddress = !addressErrorMessage;
  const isValidNickName = !nickNameErrorMessage;

  const onChangeRegister = ({ value, name }: { value: string; name: string }) => {
    setUser({ ...user, [name]: value });
  };

  const onResetRegister = () => {
    setUser({ email: '', nickname: '', oauthType: '', pageName: '' });
  };

  const registerUser = async () => {
    try {
      await requestRegister(user);
    } catch (error) {
      console.error(error.response.data.message);
      history.push('/register');
    }
  };

  return {
    pageName,
    addressErrorMessage,
    isValidAddress,
    nickname,
    nickNameErrorMessage,
    isValidNickName,
    onChangeRegister,
    onResetRegister,
    registerUser,
  };
};

export default useRegister;
