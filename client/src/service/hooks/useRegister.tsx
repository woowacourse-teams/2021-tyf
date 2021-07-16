import { useRecoilState, useRecoilValue } from 'recoil';
import { newUserState, urlNameValidationQuery } from '../state/newUser';

const useRegister = () => {
  const [user, setUser] = useRecoilState(newUserState);
  const addressErrorMessage = useRecoilValue(urlNameValidationQuery);
  // TODO: db로의 검증
  // const addressDBErrorMessage = useRecoilValueLoadable(urlNameDBValidationQuery);

  const { urlName, nickName } = user;

  const isValidAddress = !addressErrorMessage;

  const onChangeAddress = ({ value }: { value: string }) => {
    setUser({ ...user, urlName: value });
  };

  return { urlName, addressErrorMessage, isValidAddress, onChangeAddress };
};

export default useRegister;
