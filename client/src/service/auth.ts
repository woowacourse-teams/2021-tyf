import { OAuth, REDIRECT_PATH } from '../constants/oauth';
import { OAuthProvider } from '../types';

type RedirectPathType = keyof typeof REDIRECT_PATH;

export const routeToOAuthPage = (oauthName: OAuthProvider, redirectPath: RedirectPathType) => {
  window.open(OAuth[oauthName].url + redirectPath + oauthName, '_target');
};
