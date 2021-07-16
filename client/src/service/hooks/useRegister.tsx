import { useRecoilState, useRecoilValue } from 'recoil';
import { newUserState, nickNameValidationQuery, urlNameValidationQuery } from '../state/register';

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

  return {
    urlName,
    addressErrorMessage,
    isValidAddress,

    nickName,
    nickNameErrorMessage,
    isValidNickName,
    onChangeRegister,
    onResetRegister,
  };
};

export default useRegister;
