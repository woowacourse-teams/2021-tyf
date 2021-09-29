export const AUTH_CODE = 'code';
export const REDIRECT_TO_AFTER_LOGIN = 'redirectTo';

const origin =
  process.env.NODE_ENV === 'production' ? 'https://thankyou-for.com' : 'http://localhost:9000';

export const OAUTH = {
  KAKAO: {
    URL: `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=d187d243372db29c85e85eef81d4723a&redirect_uri=${origin}`,
  },
  NAVER: {
    URL: `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=ZJkZDegYIcTIZu8NP5qI&state=STATE_STRING&redirect_uri=${origin}`,
  },
  GOOGLE: {
    URL: `https://accounts.google.com/o/oauth2/v2/auth?client_id=153785509866-72pebue5t5qqcpci2d1bncrh497ootcc.apps.googleusercontent.com&response_type=code&scope=https://www.googleapis.com/auth/userinfo.profile%20email&access_type=offline&redirect_uri=${origin}`,
  },
} as const;

export const REDIRECT_PATH = {
  REGISTER: '/register/terms/',
  LOGIN: '/login/',
} as const;

export const OAUTH_ERROR = 'error';
export const OAUTH_ERROR_DESC = 'error_description';
