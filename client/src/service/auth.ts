import { OAUTH } from '../constants/oauth';

type OAuthRouteType = keyof typeof OAUTH;

export const routeToOAuthPage = (oauthName: OAuthRouteType) => {
  window.open(OAUTH[oauthName].URL, '_target');
};
