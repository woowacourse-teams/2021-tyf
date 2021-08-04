import { useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { useHistory, useParams } from 'react-router-dom';

import { ParamTypes } from '../../App';
import { OAuthProvider } from '../../types';
import { getQueryVariable } from '../../utils/queryString';
import { AUTH_CODE, OAUTH_ERROR, OAUTH_ERROR_DESC } from '../../constants/oauth';
import { requestOAuthRegister } from '../request/register';
import { newUserState } from '../state/register';

const useRegisterEffect = () => {
  const history = useHistory();
  const [user, setUser] = useRecoilState(newUserState);
  const { oauthProvider } = useParams<ParamTypes>();

  const register = async (oauthProvider: OAuthProvider, authCode: string) => {
    try {
      const { email, oauthType } = await requestOAuthRegister(oauthProvider, authCode);

      setUser({ ...user, email, oauthType });
    } catch (error) {
      if (error.response.data.errorCode === 'auth-004') {
        alert('이미 가입되어 있는 사용자입니다.');
        history.push('/login');
        return;
      }

      console.error(error.response.data.message);
      history.push('/register');
    }
  };

  const handleRedirectionError = () => {
    console.error(getQueryVariable(OAUTH_ERROR));
    console.error(getQueryVariable(OAUTH_ERROR_DESC));

    alert('회원가입 외부인증에 실패했습니다. 다시 시도해주세요.');
  };

  useEffect(() => {
    const authCode = getQueryVariable(AUTH_CODE);
    const hasError = !!getQueryVariable(OAUTH_ERROR);
    if (hasError) return handleRedirectionError();

    if (!oauthProvider || !authCode) return;

    register(oauthProvider, authCode);
  }, []);
};

export default useRegisterEffect;
