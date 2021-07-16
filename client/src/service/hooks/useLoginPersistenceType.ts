import { useRecoilState } from 'recoil';

import { loginPersistenceTypeState } from '../state/login';

const useLoginPersistenceType = () => {
  const [loginPersistenceType, setLoginPersistenceType] = useRecoilState(loginPersistenceTypeState);

  return { loginPersistenceType, setLoginPersistenceType };
};

export default useLoginPersistenceType;
