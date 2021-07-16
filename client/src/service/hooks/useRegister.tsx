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

  const onChangeAddress = ({ value }: { value: string }) => {
    setUser({ ...user, urlName: value });
  };

  const onChangeNickName = ({ value }: { value: string }) => {
    setUser({ ...user, nickName: value });
  };

  return {
    urlName,
    addressErrorMessage,
    isValidAddress,
    onChangeAddress,
    nickName,
    nickNameErrorMessage,
    isValidNickName,
    onChangeNickName,
  };
};

export default useRegister;
