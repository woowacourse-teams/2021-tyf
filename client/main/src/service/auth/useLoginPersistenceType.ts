import { useRecoilState } from 'recoil';

import { STORAGE_KEY } from '../../constants/storage';
import { StorageType } from '../../types';
import { setLocalStorageItem } from '../../utils/storage';
import { loginPersistenceTypeState } from '../@state/login';

const useLoginPersistenceType = () => {
  const [loginPersistenceType, _setLoginPersistenceType] =
    useRecoilState(loginPersistenceTypeState);

  const setLoginPersistenceType = (loginPersistenceType: StorageType) => {
    const isLoginPersist = loginPersistenceType === 'LOCAL';

    setLocalStorageItem(STORAGE_KEY.IS_LOGIN_PERSIST, isLoginPersist);
    _setLoginPersistenceType(loginPersistenceType);
  };

  return { loginPersistenceType, setLoginPersistenceType };
};

export default useLoginPersistenceType;
