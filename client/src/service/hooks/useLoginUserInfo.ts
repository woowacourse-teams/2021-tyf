import { useRecoilValueLoadable, useSetRecoilState } from 'recoil';

import { LoginUserInfo } from '../../types';
import { accessTokenState, loginUserInfoQuery } from '../state/login';

interface useLoginUserInfoReturnType {
  userInfo: LoginUserInfo | null;
  hasError: boolean;
}

const useLoginUserInfo = (): useLoginUserInfoReturnType => {
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

  console.log(contents);

  return { userInfo: contents as LoginUserInfo, hasError: false };
};

export default useLoginUserInfo;
