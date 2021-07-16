import { useRecoilState, useRecoilValue } from 'recoil';
import { newUserState, urlNameValidationQuery } from '../state/newUser';

const useRegister = () => {
  const [user, setUser] = useRecoilState(newUserState);
  const addressErrorMessage = useRecoilValue(urlNameValidationQuery);

  const { urlName } = user;

  const isValidAddress = !addressErrorMessage;

  const onChangeAddress = ({ value }: { value: string }) => {
    setUser({ ...user, urlName: value });
  };

  return { urlName, addressErrorMessage, isValidAddress, onChangeAddress };
};

export default useRegister;
