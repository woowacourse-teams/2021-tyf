import { useEffect } from 'react';
import { useHistory } from 'react-router-dom';

import { AUTH_ERROR } from '../../constants/error';
import { AUTH_CODE, OAUTH_ERROR, OAUTH_ERROR_DESC } from '../../constants/oauth';
import { OAuthProvider } from '../../types';
import { getQueryVariable } from '../../utils/queryString';
import { requestLogin } from '../@request/auth';
import useAccessToken from '../@shared/useAccessToken';

const useLoginEffect = (oauthProvider?: OAuthProvider) => {
  const history = useHistory();
  const { storeAccessToken } = useAccessToken();

  const login = async (oauthProvider: OAuthProvider, authCode: string) => {
    try {
      const { token } = await requestLogin(oauthProvider, authCode);

      storeAccessToken(token);
      history.go(-3);
    } catch (error) {
      if (error.response.data.errorCode === AUTH_ERROR.NOT_USER) {
        alert('회원가입 페이지로 이동합니다.');
        history.push('/register');
      }
    }
  };

  const handleRedirectionError = () => {
    console.error(getQueryVariable(OAUTH_ERROR));
    console.error(getQueryVariable(OAUTH_ERROR_DESC));

    alert('로그인에 실패했습니다. 다시 시도해주세요.');
  };

  useEffect(() => {
    const authCode = getQueryVariable(AUTH_CODE);
    const hasError = !!getQueryVariable(OAUTH_ERROR);

    if (hasError) return handleRedirectionError();

    if (!oauthProvider || !authCode) return;

    login(oauthProvider, authCode);
  }, []);
};

export default useLoginEffect;
