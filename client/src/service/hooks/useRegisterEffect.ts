import { useEffect } from 'react';
import { useHistory, useParams } from 'react-router-dom';

import { ParamTypes } from '../../App';
import { OAuthProvider } from '../../types';
import { getQueryVariable } from '../../utils/queryString';
import { AUTH_CODE, OAUTH_ERROR, OAUTH_ERROR_DESC } from '../../constants/oauth';
import { requestOAuthRegister } from '../request/register';

const useRegisterEffect = () => {
  const history = useHistory();
  const { oauthProvider } = useParams<ParamTypes>();

  console.log('useEffect');

  const register = async (oauthProvider: OAuthProvider, authCode: string) => {
    try {
      await requestOAuthRegister(oauthProvider, authCode);
    } catch (error) {
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
    console.log(authCode, hasError, oauthProvider);
    if (hasError) return handleRedirectionError();

    if (!oauthProvider || !authCode) return;

    register(oauthProvider, authCode);
  }, []);
};

export default useRegisterEffect;
