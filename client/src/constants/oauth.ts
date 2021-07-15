export const AUTH_CODE = 'code';

export const OAUTH = {
  KAKAO: {
    URL: 'https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=d187d243372db29c85e85eef81d4723a&redirect_uri=http://localhost:9000',
  },
  NAVER: {
    URL: '',
  },
  GOOGLE: {
    URL: 'https://accounts.google.com/o/oauth2/v2/auth?client_id=153785509866-72pebue5t5qqcpci2d1bncrh497ootcc.apps.googleusercontent.com&response_type=code&scope=https://www.googleapis.com/auth/userinfo.profile email&access_type=offline&redirect_uri=http://localhost:9000',
  },
} as const;

export const REDIRECT_PATH = {
  REGISTER: '/register/url',
  LOGIN: '/login/',
} as const;

export const OAUTH_ERROR = 'error';
export const OAUTH_ERROR_DESC = 'error_description';
