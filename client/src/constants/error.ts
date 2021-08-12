export enum AUTH_ERROR {
  INVALID_TOKEN = 'auth-002',
  ALREADY_REGISTER = 'auth-004',
  NOT_USER = 'auth-006',
}

export const AUTH_ERROR_MESSAGE = {
  [AUTH_ERROR.INVALID_TOKEN]: '유효하지 않은 토큰입니다.',
  [AUTH_ERROR.ALREADY_REGISTER]: '이미 가입되어 있는 사용자입니다.',
  [AUTH_ERROR.NOT_USER]: '유저가 아닙니다.',
} as const;

export enum PAYMENT_ERROR {
  EXCEED_TRY_COUNT = 'payment-012',
}

export const PAYMENT_ERROR_MESSAGE = {
  [PAYMENT_ERROR.EXCEED_TRY_COUNT]: '인증 횟수를 모두 소진하셨었습니다. 고객센터로 연락해주세요',
};
