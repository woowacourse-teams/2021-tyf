import { OAUTH, REDIRECT_PATH } from '../constants/oauth';
import { OAuthProvider } from '../types';

type RedirectPathType = keyof typeof REDIRECT_PATH;

export const routeToOAuthPage = (oauthName: OAuthProvider, redirectPath: RedirectPathType) => {
  window.open(OAUTH[oauthName].URL + redirectPath + oauthName, '_target');
};
