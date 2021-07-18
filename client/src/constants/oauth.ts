export const AUTH_CODE = 'code';

const origin = window.location.origin;

export const OAUTH = {
  KAKAO: {
    URL: `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=ff77c9c32b464ffb0d98f3d04f7a7a48&redirect_uri=${origin}`,
  },
  NAVER: {
    URL: `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=ZJkZDegYIcTIZu8NP5qI&state=STATE_STRING&redirect_uri=${origin}`,
  },
  GOOGLE: {
    URL: `https://accounts.google.com/o/oauth2/v2/auth?client_id=153785509866-72pebue5t5qqcpci2d1bncrh497ootcc.apps.googleusercontent.com&response_type=code&scope=https://www.googleapis.com/auth/userinfo.profile%20email&access_type=offline&redirect_uri=${origin}`,
  },
} as const;

export const REDIRECT_PATH = {
  REGISTER: '/register/url',
  LOGIN: '/login/',
} as const;

export const OAUTH_ERROR = 'error';
export const OAUTH_ERROR_DESC = 'error_description';
