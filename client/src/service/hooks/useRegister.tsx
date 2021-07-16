import { useRecoilState, useRecoilValue } from 'recoil';

import { newUserState, nickNameValidationQuery, urlNameValidationQuery } from '../state/register';
import { requestRegister } from '../request/register';

const useRegister = () => {
  const [user, setUser] = useRecoilState(newUserState);
  const addressErrorMessage = useRecoilValue(urlNameValidationQuery);
  const nickNameErrorMessage = useRecoilValue(nickNameValidationQuery);

  // TODO: db로의 검증
  // const addressDBErrorMessage = useRecoilValueLoadable(urlNameDBValidationQuery);
  // const nickNameDBErrorMessage = useRecoilValueLoadable(nickNameDBValidationQuery);

  const { urlName, nickName } = user;

  const isValidAddress = !addressErrorMessage;
  const isValidNickName = !nickNameErrorMessage;

  const onChangeRegister = ({ value, name }: { value: string; name: string }) => {
    setUser({ ...user, [name]: value });
  };

  const onResetRegister = () => {
    setUser({ email: '', nickName: '', oauthType: '', urlName: '' });
  };

  const registerUser = async () => {
    try {
      await requestRegister(user);
    } catch (error) {
      alert(error.message);
    }
  };

  return {
    urlName,
    addressErrorMessage,
    isValidAddress,
    nickName,
    nickNameErrorMessage,
    isValidNickName,
    onChangeRegister,
    onResetRegister,
    registerUser,
  };
};

export default useRegister;
