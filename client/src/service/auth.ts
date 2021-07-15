import { OAUTH } from '../constants/constant';

type OAuthRouteType = keyof typeof OAUTH;

export const routeToOAuthPage = (name: OAuthRouteType) => {
  window.open(OAUTH[name].URL, '_target');
};
