import { useRecoilValueLoadable, useSetRecoilState } from 'recoil';

import { UserInfo } from '../../types';
import { accessTokenState, loginUserInfoQuery } from '../state/login';

interface useUserInfoReturnType {
  userInfo: UserInfo | null;
  hasError: boolean;
}

const useUserInfo = (): useUserInfoReturnType => {
  const { contents, state } = useRecoilValueLoadable(loginUserInfoQuery);
  const setAccessToken = useSetRecoilState(accessTokenState);

  if (state === 'hasError') {
    setAccessToken('');
    console.error(contents.errorCode);
    console.error(contents.message);

    return { userInfo: null, hasError: true };
  }

  if (state === 'loading') {
    return { userInfo: null, hasError: false };
  }

  return { userInfo: contents as UserInfo, hasError: false };
};

export default useUserInfo;
