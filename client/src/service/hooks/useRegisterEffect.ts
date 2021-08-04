import { useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { useHistory, useParams } from 'react-router-dom';

import { ParamTypes } from '../../App';
import { OAuthProvider } from '../../types';
import { getQueryVariable } from '../../utils/queryString';
import { AUTH_CODE, OAUTH_ERROR, OAUTH_ERROR_DESC } from '../../constants/oauth';
import { requestOAuthRegister } from '../request/register';
import { newUserState } from '../state/register';
import { AUTH_ERROR, AUTH_ERROR_MESSAGE } from '../../constants/error';

const useRegisterEffect = () => {
  const history = useHistory();
  const [user, setUser] = useRecoilState(newUserState);
  const { oauthProvider } = useParams<ParamTypes>();

  const register = async (oauthProvider: OAuthProvider, authCode: string) => {
    try {
      const { email, oauthType } = await requestOAuthRegister(oauthProvider, authCode);

      setUser({ ...user, email, oauthType });
    } catch (error) {
      const { message, errorCode } = error.response.data;

      if (errorCode === AUTH_ERROR.ALREADY_REGISTER) {
        alert(AUTH_ERROR_MESSAGE[AUTH_ERROR.ALREADY_REGISTER]);
        history.push('/login');
        return;
      }

      console.error(message);
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
