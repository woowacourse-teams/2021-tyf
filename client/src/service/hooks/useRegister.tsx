import { useRecoilState, useRecoilValue } from 'recoil';
import { requestRegister } from '../request/register';
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

  const registerUser = async () => {
    try {
      await requestRegister(user);
    } catch (error) {
      alert(error.message ?? '회원가입에 실패했습니다.');
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
